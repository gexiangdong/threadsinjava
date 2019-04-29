package cn.devmgr.javathreads.section8;

import java.util.ArrayList;
import java.util.Comparator;
import java.util.List;
import static java.util.Comparator.comparing;

public class EmployeeSorting {

    public static void main(String[] argvs){
        List<Employee> list = new ArrayList<>();

        Employee e1 = new Employee();
        e1.setName("Tom");
        list.add(e1);
        Employee e2 = new Employee();
        e2.setName("Alice");
        list.add(e2);

        // 排序list

        // 方法一：
        list.sort(new Comparator<Employee>() {
            @Override
            public int compare(Employee o1, Employee o2) {
                return o1.getName().compareTo(o2.getName());
            }
        });

        // 用lambda表达式简化方法一
        list.sort((Employee o1, Employee o2) -> { return o1.getName().compareTo(o2.getName()); });

        //再次简化方法一
        list.sort((o1, o2) -> o1.getName().compareTo(o2.getName()));


        // 方法二： 利用Compator的comparing方法
        list.sort(Comparator.comparing((Employee employee) -> { return employee.getName();  }));

        // 简化方法二写法
        list.sort(Comparator.comparing(employee -> employee.getName()));

        // 使用eta转换（方法引用）再次简化方法二
        list.sort(Comparator.comparing(Employee::getName));

        //System.out.println(list.get(0).getName() + ", " + list.get(1).getName());

        // 使用了 import static java.util.Comparator.comparing; 后，可以更短
        list.sort(comparing(Employee::getName));

    }

    public static class Employee{
        private String name;

        public String getName() {
            return name;
        }

        public void setName(String name) {
            this.name = name;
        }
    }
}
