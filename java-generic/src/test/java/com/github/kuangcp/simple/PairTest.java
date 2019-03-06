package com.github.kuangcp.simple;

import com.github.kuangcp.common.Human;
import com.github.kuangcp.common.Student;
import java.util.Date;
import org.junit.Test;

/**
 * Created by https://github.com/kuangcp on 18-1-11  下午5:38
 * 这个泛型类的使用就有点像集合的泛型使用了
 *
 * TODO 整理, 删除无用注释
 *
 * @author kuangcp
 */
public class PairTest {

  private class Junior extends Student {

  }

  @Test
  public void testSimple() {
    Pair<Date> pair = new Pair<>();
    pair.setFirst(new Date());
    System.out.println(pair.getFirst());

    // 编译期 泛型类型检查, Pair的泛型类型已经声明为Date, 所以以下编译通不过
//  x  pair.setSecond(new Integer(3));
  }

  @Test
  public void testMinMax() {
    Float[] arrays = {2.1f, 4.2f, 3.5f};
    Pair<Float> pair;
    pair = Pair.minAndMax(arrays);
    System.out.println(pair.getFirst() + "--" + pair.getSecond());
  }

  /**
   * 使用extends
   */
  @Test
  public void testExtendsWithSet() {
    Pair<Human> humans = new Pair<>();
    humans.setFirst(new Human());
    humans.setSecond(new Student());

//  x  humans.setSecond(new Object());

    Pair<? extends Human> classmates = humans;

    // TODO not compile ?
//    classmates.setFirst(new Human());
//    classmates.setSecond(new Student());
//    classmates.setSecond(new Object());

    // 原始类型的坑
    ((Pair) classmates).setFirst("str");
    System.out.println(classmates.getFirst());

    ((Pair<Human>) classmates).setFirst(new Human("name"));

    // 虽然通过了编译,也运行正常,但是该泛型程序没有实现其需要的目的(类型约束)
    // 原本应该是Human对象才能set,但是变成原始类型即多态后就能set任意对象了
    System.out.println(classmates.getFirst());
  }

  //  以下是关于通配符, 说的set和get是指 泛型类Pair 上的成员属性的set和get方法

  /**
   * 通配符类型的 类型变量 的约束 extends
   *
   * @param student 使用了通配符限定的泛型约束的参数
   * 被限定的参数 student 被传参,只能是子类型放进去
   * 被限定的参数,进行操作时 才有限制: 不能set只能get
   * get的反而是 自己和 父类型(这个并不是泛型的作用吧,而是多态?)
   */
  public Pair<? extends Student> printMessage(Human obj, Pair<? extends Student> student) {
    Human first = student.getFirst();
    Student second = student.getFirst();// 只是因为多态?

//        student.setSecond(obj);
    System.out.println(first);
    //        Student second = human.getSecond(); // 正常: 限定了是Human子类
    return student;
  }

  /**
   * 通配符类型的 超类型限定的 类型变量 约束 super
   *
   * @param student 资源
   * @param result 使用了通配符的超类型的泛型约束的参数
   * 被限定的参数 result 被传参,只能是父类型放进去
   * 被限定的参数,进行操作时 才有限制: 不能get(失去了约束)只能set
   * set的是Student自己或子类,虽然是super关键字,但是限定的还是子类型范围
   */
  public Pair<? super Student> minMaxBonus(Student student, Pair<? super Student> result) {
    result.setFirst(student);
    result.setSecond(new Junior());

    Object resultSecond = result.getSecond(); // 失去了泛型约束
//        Human human = result.getSecond();
    return result;
  }

  // extends 只能get
  @Test
  public void testExtends() {
    Pair<Junior> pair = new Pair<>();
    Pair result = printMessage(new Human("name"), pair);
//    Pair<Human> result = printMessage(new Human("name"), pair); // 可以用原始类型接收但是不能用 父类的类型变量约束的
  }

  // super 只能set
  @Test
  public void testSuper() {
    Pair<Human> pair = new Pair<>();
//        Pair<Junior> pair = new Pair<>(); // 报错限定了是泛型约束变量类型的 自己和父类 子类是不允许的
    Pair result = minMaxBonus(new Student("how"), pair);
//        Pair<Human> result = minMaxBonus(new Student("how"), pair); // 可以用原始类型接收但是不能用 父类的类型变量约束的
    System.out.println(result.getFirst().toString());

  }

  // 无限定通配符

  /**
   * 判断Pair是否是空指针
   * 通过将contains转换成泛型方法 , 可以避免使用通配符类型;
   * public static <T> boolean hasNull(Pair<T> p)
   * 但是带有通配符的版本可读性更强.
   *
   * @param p 泛型变量约束的类, 不需要实际的类型
   * @return boolean 判断两个属性是否有一个是空.
   */
  public boolean hasNull(Pair<?> p) {
    // get方法返回值只能返回给Object, set方法不能被调用, 甚至不能用Object调用
    return p.getFirst() == null || p.getSecond() == null;
  }

  // 简单使用 T 来进行约束, 所以这俩方法有啥区别呢?
  // 作为通配符不能使用T
//    public boolean hasNulls(Pair<T> p){
//        return p.getSecond() == null || p.getFirst() == null;
//    }
  @Test
  public void testHasNull() {
    Pair<Human> humanPair = new Pair<>();
    humanPair.setSecond(new Human("fds"));
    boolean result = hasNull(humanPair);
    System.out.println("结果:" + result);
  }

  @Test
  public void testHasNulls() {
    Pair<Human> humanPair = new Pair<>();
    humanPair.setSecond(new Human("fds"));
    humanPair.setFirst(new Student("df"));
    System.out.println(humanPair.getFirst().toString());
  }
}