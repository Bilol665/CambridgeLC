package uz.pdp.cambridgelc.service.course;

import jakarta.transaction.Transactional;
import lombok.RequiredArgsConstructor;
import org.apache.poi.hssf.usermodel.HSSFFont;
import org.apache.poi.ss.usermodel.*;
import org.apache.poi.xssf.usermodel.XSSFWorkbook;
import org.modelmapper.ModelMapper;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Sort;
import org.springframework.stereotype.Service;
import org.springframework.validation.BindingResult;
import org.springframework.validation.ObjectError;
import uz.pdp.cambridgelc.entity.course.CourseEntity;
import uz.pdp.cambridgelc.entity.course.CourseLevel;
import uz.pdp.cambridgelc.entity.dto.CourseDto;
import uz.pdp.cambridgelc.exceptions.DataNotFoundException;
import uz.pdp.cambridgelc.exceptions.RequestValidationException;
import uz.pdp.cambridgelc.repository.CourseRepository;

import java.io.FileOutputStream;
import java.io.IOException;
import java.time.format.DateTimeFormatter;
import java.util.List;
import java.util.UUID;

@Service
@RequiredArgsConstructor
@Transactional
public class CourseService {
    private final CourseRepository courseRepository;
    private final ModelMapper modelMapper;
    private final String courseXlsPath;

    public CourseEntity addCourse(CourseDto courseDto, BindingResult bindingResult) {
        if (bindingResult.hasErrors()) {
            List<ObjectError> errors = bindingResult.getAllErrors();
            throw new RequestValidationException(errors);
        }
        CourseEntity course = modelMapper.map(courseDto, CourseEntity.class);
        try {
            course.setLevel(CourseLevel.valueOf(courseDto.getLevel()));
            return courseRepository.save(course);
        } catch (Exception e) {
            throw new DataNotFoundException("Course level not found");
        }
    }

    public void deleteByTitle(String title) {
        courseRepository.removeCourseEntityByTitle(title);
    }

    public CourseEntity updateSupport(CourseDto courseDto, UUID id, BindingResult bindingResult) {
        if (bindingResult.hasErrors()) {
            List<ObjectError> errors = bindingResult.getAllErrors();
            throw new RequestValidationException(errors);
        }
        CourseEntity course = courseRepository.findById(id).orElseThrow(
                () -> new DataNotFoundException("Course not found!")
        );
        modelMapper.map(courseDto, course);
        return courseRepository.save(course);
    }

    public CourseEntity updateTeacher(String title, UUID id) {
        CourseEntity course = courseRepository.findById(id).orElseThrow(
                () -> new DataNotFoundException("Course not found")
        );
        course.setTitle(title);
        return courseRepository.save(course);
    }

    public List<CourseEntity> getCoursesByLevel(String level) {
        CourseLevel courseLevel;
        try {
            courseLevel = CourseLevel.valueOf(level);
        } catch (IllegalArgumentException e) {
            throw new DataNotFoundException("Course not found!");
        }
        return courseRepository.findByLevel(courseLevel);
    }

    public List<CourseEntity> getToExcel(int page, int size) {
        Pageable pageable = PageRequest.of(page, size, Sort.by("createdDate").descending());
        List<CourseEntity> courses = courseRepository.findAll(pageable).getContent();
        try (Workbook workbook = new XSSFWorkbook()) {
            int rowCount = 0;

            Sheet sheet = workbook.createSheet("Courses");
            Row row = sheet.createRow(rowCount++);

            CellStyle style = workbook.createCellStyle();
            Font font = workbook.createFont();
            font.setFontName(HSSFFont.FONT_ARIAL);
            font.setFontHeightInPoints((short) 10);
            font.setBold(true);
            style.setFillForegroundColor(IndexedColors.LIGHT_BLUE.getIndex());
            style.setFillPattern(FillPatternType.SOLID_FOREGROUND);
            style.setFont(font);

            Cell cell0 = row.createCell(0);
            cell0.setCellValue("Title");
            cell0.setCellStyle(style);

            Cell cell1 = row.createCell(1);
            cell1.setCellValue("Duration");
            cell1.setCellStyle(style);

            Cell cell2 = row.createCell(2);
            cell2.setCellValue("Level");
            cell2.setCellStyle(style);

            Cell cell3 = row.createCell(3);
            cell3.setCellValue("Price");
            cell3.setCellStyle(style);

            Cell cell4 = row.createCell(4);
            cell4.setCellValue("Created date");
            cell4.setCellStyle(style);

            int ko = 0;
            for (CourseEntity course : courses) {
                Row row1 = sheet.createRow(rowCount++);
                row1.createCell(ko++).setCellValue(course.getTitle());
                row1.createCell(ko++).setCellValue(course.getDuration());
                row1.createCell(ko++).setCellValue(course.getLevel().toString());
                row1.createCell(ko++).setCellValue(course.getPrice());
                DateTimeFormatter formatter = DateTimeFormatter.ofPattern("yyyy-MM-dd HH:mm:ss");
                row1.createCell(ko).setCellValue(course.getCreatedDate().format(formatter));
                ko = 0;
            }
            for (int i = 0; i < row.getLastCellNum(); i++) {
                sheet.autoSizeColumn(i);
            }
            FileOutputStream out = new FileOutputStream(courseXlsPath);
            workbook.write(out);
            out.close();

            return courses;
        } catch (IOException e) {
            throw new RuntimeException(e);
        }
    }
}
