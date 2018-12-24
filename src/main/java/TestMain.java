import dao.BookDao;
import dao.BookDaoImpl;
import domain.Book;
import domain.Classes;
import domain.Student;
import domain.Teacher;
import interceptor.Page;
import org.apache.ibatis.session.SqlSession;
import util.SessionFactoryUtil;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

public class TestMain {

    public static void main(String[] args) {
//        SqlSession session = SessionFactoryUtil.getSession("development");
        SqlSession session = SessionFactoryUtil.getSession("beta");
        List<Student> students =  session.selectList("dao.StudentDao.findAll");
        System.out.println("dao.StudentDao.findAll:");
        for(Student s : students) {
            System.out.println("sid = " + s.getSid() + " | studentName = " + s.getStudentName());
        }
    }

    /**
     * 多数据源配置
     */
    public static void multDB() {
        //        SqlSession session = SessionFactoryUtil.getSession("development");
        SqlSession session = SessionFactoryUtil.getSession("beta");
        List<Student> students =  session.selectList("dao.StudentDao.findAll");
        System.out.println("dao.StudentDao.findAll:");
        for(Student s : students) {
            System.out.println("sid = " + s.getSid() + " | studentName = " + s.getStudentName());
        }
    }

    /**
     * 分页查找示例
     * 1、继承Interceptor实现分页拦截器
     * 2、Page实体类
     * 3、config中配置拦截器（plugins标签）
     */
    public static void pageTest() {
        SqlSession session = SessionFactoryUtil.getSession("development");
        Page page = new Page();
        page.setCurrentPage(2);
        page.setPageNumber(2);
        Map map = new HashMap();
        map.put("page", page);
        map.put("name", "%学生%");
        //List<Student> students =  session.selectList("dao.StudentDao.findByPage", map);
        List<Student> students =  session.selectList("dao.StudentDao.findStudentLikeNameByPage", map);
        System.out.println("dao.StudentDao.findByPage:");
        for(Student s : students) {
            System.out.println("sid = " + s.getSid() + " | studentName = " + s.getStudentName());
        }
    }

    /**
     * 枚举映射示例
     * 1、继承BaseTypeHandler实现类型handler
     * 2、实现公用枚举接口
     * 3、实现具体枚举类
     * 4、对应字段引入枚举类型（<result column="sex" property="sex" typeHandler="handler.EnumKeyTypeHandler" javaType="handler.SexEnum"/>）
     */
    public static void enumTest() {
        SqlSession session = SessionFactoryUtil.getSession("development");
        Student student = session.selectOne("dao.StudentDao.findOneStudent", 1L);
        System.out.println("dao.StudentDao.findOneStudent: \nstudentName = " + student.getStudentName() + "\n");
        System.out.println("sex: name = " + student.getSex().getName() + " ,value = " + student.getSex().getValue());
        System.out.println("country: name = " + student.getCountry().getName() + " ,value = " + student.getCountry().getValue());
    }

    //映射关系相关查询示例
    public static void teacherStudentTest() {
        SqlSession session = SessionFactoryUtil.getSession("development");
        Student student = session.selectOne("dao.StudentDao.findOneStudent", 1L);
        System.out.println("dao.StudentDao.findOneStudent: \nstudentName = " + student.getStudentName() + "\nclasses name = " + student.getClasses().getClassesName() + "\n");

        Classes classes = session.selectOne("dao.ClassesDao.findOneClasses", 2L);
        System.out.println("dao.ClassesDao.findOneClasses: \nclasses name = " + classes.getClassesName() + ", students size = " + classes.getStudents().size());
        for(Student s : classes.getStudents()) {
            System.out.println("student name = " + s.getStudentName());
        }


        Student s1 = new Student();
        s1.setStudentName("学生10");
        s1.setClasses(classes);
        Student s2 = new Student();
        s2.setStudentName("学生11");
        s2.setClasses(classes);
        List<Student> students = new ArrayList();
        students.add(s1);
        students.add(s2);
        session.insert("dao.StudentDao.insertStudents", students);
        for(Student s : students) {
            System.out.println("dao.StudentDao.insertStudents: s1.sid = " + s.getSid() + ", s2.studentName = " + s.getStudentName());
        }

        Teacher teacher = new Teacher();
        teacher.setTeacherName("teacher1");
        session.insert("dao.TeacherDao.insertOne", teacher);
        System.out.println("dao.TeacherDao.insertOne: teacher id = " + teacher.getTid());

        Map map = new HashMap();
        map.put("teacher", teacher);
        map.put("students", students);
        session.insert("dao.TeacherDao.insertTeacherStudent", map);
        session.commit();
    }

    //简单增删改查示例
    public static void bookTest() {
        BookDao bookDao = new BookDaoImpl();
        List<Book> books = bookDao.findAll();
        System.out.println("findAll: " + books.size());

        Book book = bookDao.findOne(1L);
        System.out.println("findOne: " + book.getName());

        /*Book book1 = new Book();
        book1.setName("book3");
        bookDao.addBook(book1);
        System.out.println("addBook: " + book1.getId());*/

        List ids = new ArrayList();
        ids.add(1);
        ids.add(2);
        ids.add(6);
        books = bookDao.findByIdWithIn(ids);
        System.out.println("findByIdWithIn: " + books.size());

        List names = new ArrayList();
        names.add("'book1'");
        names.add("'book3'");
        Map map = new HashMap();/*多个参数用map传递*/
        map.put("ids", ids);
        map.put("names", names);
        books = bookDao.findByIdAndNameWithIn(map);
        System.out.println("findByIdAndNameWithIn: " + books.size());

        //不写接口的情况下实现查询
        SqlSession session = SessionFactoryUtil.getSession("development");
        Book book2 = session.selectOne("dao.BookDao.findByName", "book1");
        System.out.println("BookDao.findByName: name = " + book2.getName() + ", id = " + book2.getId());

        List results = session.selectList("dao.BookDao.findAll");
        System.out.println("BookDao.findAll: size = " + results.size());

        List toMapList = session.selectList("dao.BookDao.findAllToMap");
        for(Object o : toMapList) {
            HashMap m = (HashMap) o;
            System.out.println("BookDao.findAllToMap: id = " + m.get("id") + ", name = " + m.get("name"));
        }
    }
}
