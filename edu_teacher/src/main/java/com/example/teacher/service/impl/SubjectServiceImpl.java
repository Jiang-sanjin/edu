package com.example.teacher.service.impl;


import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import com.example.teacher.mapper.SubjectMapper;
import com.example.teacher.pojo.Subject;
import com.example.teacher.pojo.vo.OneSubject;
import com.example.teacher.pojo.vo.TwoSubject;
import com.example.teacher.service.SubjectService;
import org.apache.poi.hssf.usermodel.HSSFSheet;
import org.apache.poi.hssf.usermodel.HSSFWorkbook;
import org.apache.poi.ss.usermodel.Cell;
import org.apache.poi.ss.usermodel.Row;
import org.apache.poi.ss.usermodel.Sheet;
import org.springframework.beans.BeanUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.util.StringUtils;
import org.springframework.web.multipart.MultipartFile;

import java.io.IOException;
import java.io.InputStream;
import java.util.ArrayList;
import java.util.List;

/**
 * <p>
 * 课程科目 服务实现类
 * </p>
 *
 * @author jiangsanjin
 * @since 2021-03-26
 */
@Service
public class SubjectServiceImpl extends ServiceImpl<SubjectMapper, Subject> implements SubjectService {

    @Autowired
    private SubjectMapper subjectMapper;

    @Override
    public List<String> importEXCL(MultipartFile file) {

        //存储错误信息集合
        List<String> message = new ArrayList<>();


        try {
            // 1.获取文件流
            InputStream inputStream = file.getInputStream();
            // 2.根据流创建workbook
            HSSFWorkbook workbook = new HSSFWorkbook(inputStream);
            // 3.获取sheet  getSheetAt(0)
            Sheet sheet = workbook.getSheetAt(0);
            // 4.根据sheet获取行数  获取最后一个
            int lastRowNum = sheet.getLastRowNum();
            if(lastRowNum <= 1){
                message.add("请填写数据");
                return message;
            }
            // 5.遍历行
            for(int rowNum = 1; rowNum <= lastRowNum; rowNum++){
                Row row = sheet.getRow(rowNum);
                // 6.获取每行的第一列：一级分类
                Cell cell = row.getCell(0);
                if(cell == null){
                    message.add("第"+ rowNum + "行第1列为空");
                    continue;
                }
                String cellValue = cell.getStringCellValue();
                if(StringUtils.isEmpty(cellValue)){
                    message.add("第"+ rowNum + "行第1列数据为空");
                    continue;
                }

                // 7.判断是否存在，获取列的数据
                Subject subject = this.selectSubjectByName(cellValue);
                String pid = null;

                //8、把这个第一列中的数据（一级分类）保存到数据库中
                if(subject == null){
                    //9、在保存之前判断此一级分类是否存在，如果存在，不再添加；如果不存在再保存
                    Subject su = new Subject();
                    su.setTitle(cellValue);
                    su.setParent_id("0");
                    su.setSort(0);
                    subjectMapper.insert(su);
                    pid = su.getId();
                } else{
                    pid = subject.getId();  //二级分类
                }
                //10、再获取每一行的第二列
                Cell cell1 = row.getCell(1);
                //11、获取第二列中的数据（二级分类）
                if(cell1 == null){
                    message.add("第"+ rowNum + "行第2列为空");
                    continue;
                }
                String stringCellValue = cell1.getStringCellValue();
                // System.out.println("数据"+stringCellValue);
                if(StringUtils.isEmpty(stringCellValue) ){
                    // System.out.println("第"+ rowNum + "行第2列为空");
                    message.add("第"+ rowNum + "行第2列为空");
                    continue;
                }
                //12、判断此一级分类中是否存在此二级分类
                Subject subject2 = this.selectSubjectByNameAndParentId(stringCellValue, pid);
                //13、如果此一级分类中有此二级分类：不保存
                if(subject2 == null){ //14、如果没有保存二级分类
                    Subject su = new Subject();
                    su.setTitle(stringCellValue);
                    su.setParent_id(pid);
                    su.setSort(0);
                    subjectMapper.insert(su);
                }
            }

        } catch (IOException e) {
            e.printStackTrace();
        }

        return message;
    }



    /**
     * 根据二级分类名称和parentID查询是否存在Subject
     * @param stringCellValue
     * @param pid
     * @return
     */
    private Subject selectSubjectByNameAndParentId(String stringCellValue, String pid) {

        Subject subject = subjectMapper.selectOne(new QueryWrapper<Subject>().eq("title",stringCellValue).eq("parent_id",pid));
        return subject;
    }

    /**
     * 根据课程一级分类的名字查询是否存在
     * @param cellValue
     * @return
     */
    private Subject selectSubjectByName(String cellValue) {

        Subject subject = subjectMapper.selectOne(new QueryWrapper<Subject>().eq("title",cellValue).eq("parent_id",0));
        return subject;
    }



    /**
     * 获取课程分类的Tree
     * @return
     */
    @Override
    public List<OneSubject> getTree() {
        // 1、创建一个集合存放OneSubject
        List<OneSubject> oneSubjectList = new ArrayList<>();
        // 2、获取一级分类的列表

        List<Subject> eduSubjectList = subjectMapper.selectList(new QueryWrapper<Subject>().eq("parent_id",0));
        // 3、遍历一级分类的列表
        for(Subject subject : eduSubjectList){
            //4、一级分类的数据复制到OneSubject
            OneSubject oneSubject = new OneSubject();
            BeanUtils.copyProperties(subject, oneSubject);  //将subject 赋值给 oneSubject
            //5、根据每一个一级分类获取二级分类的列表
            List<Subject> eduSubjects = subjectMapper.selectList(new QueryWrapper<Subject>().eq("parent_id",oneSubject.getId()));
            // 6、遍历二级分类列表
            for(Subject su : eduSubjects){
                //7、把二级分类对象复制到TwoSubject
                TwoSubject twoSubject = new TwoSubject();
                BeanUtils.copyProperties(su, twoSubject);
                //8、 把TwoSubject添加OneSubject 的children集合中
                oneSubject.getChildren().add(twoSubject);
            }

            // 9、把OneSubject添加到集合中
            oneSubjectList.add(oneSubject);
        }

        return oneSubjectList;
    }

    /**
     * 根据id删除课程
     * @param id
     * @return
     */
    @Override
    public boolean deleteById(String id) {
        // 查询是否存在   存在二级节点（parent_id）不可删除
        List<Subject> subjectList = subjectMapper.selectList(new QueryWrapper<Subject>().eq("parent_id",id));
        if(subjectList.size() != 0){
            //如果有，返回false
            return false;
        }
        int flag = subjectMapper.deleteById(id);
        // 如果没有直接删除并返回他true
        return flag == 1;

    }

    /**
     * 新增一级分类
     * @param subject
     * @return
     */
    @Override
    public boolean saveLevelOne(Subject subject) {
        //根据一级菜单查询是否存在
        Subject eduSubject = this.selectSubjectByName(subject.getTitle());
        if(eduSubject != null){
            return false;
        }
        //不存在保存到数据库并返回true
        subject.setSort(0);
        int insert = subjectMapper.insert(subject);
        return insert == 1;

    }

    @Override
    public boolean saveLevelTwo(Subject subject) {
        //判断此一级分类中是否存在此二级分类的title

        Subject sub = this.selectSubjectByNameAndParentId(subject.getTitle(), subject.getParent_id());
        if(sub != null){
            //存在
            return false;
        }
        int insert = subjectMapper.insert(subject);
        return insert == 1;
    }


}
