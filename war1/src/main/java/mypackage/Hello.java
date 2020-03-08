package mypackage;

import java.io.IOException;
import java.io.PrintWriter;

import javax.ejb.EJB;
import javax.servlet.ServletException;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.apache.commons.io.IOUtils;

import ejb1.LocalEJB1;
import ejb1.RemoteEJB1;

public class Hello extends HttpServlet {

    private static final long serialVersionUID = 2109772982434503287L;

    @EJB(lookup = "java:global/myeeapps-ear1-0.0.1/myeeapps-ejb1-0.0.1/MyEJB1")
    private LocalEJB1 localEjb1;

    @EJB
    private RemoteEJB1 remoteEjb1;

    @Override
    protected void doGet(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
        PrintWriter writer = resp.getWriter();
        writer.println("hello");
        try {
            writer.println(IOUtils.class);
        } catch (Exception e) {
            writer.println("no deps");
        }

        long[] times = new long[5000];
        long pnanoTime = System.nanoTime();
        for (int i = 0; i < times.length; i++) {
            localEjb1.helloLocal(5, 10);
            long nanoTime = System.nanoTime();
            times[i] = nanoTime - pnanoTime;
            pnanoTime = nanoTime;
        }
        StatPrinter.stat(times);

        pnanoTime = System.nanoTime();
        for (int i = 0; i < times.length; i++) {
            remoteEjb1.helloRemote(5, 10);
            long nanoTime = System.nanoTime();
            times[i] = nanoTime - pnanoTime;
            pnanoTime = nanoTime;
        }
        StatPrinter.stat(times);

        System.out.println("end of get");
    }

    public static void main(String[] args) {
        System.out.println(System.getProperty("java.class.path"));
        System.out.println(Hello.class.getResource("a.txt"));
        System.out.println(Hello.class.getResource(""));
        System.out.println(Hello.class.getResource("/"));
        System.out.println(Hello.class.getClassLoader().getResource(""));
        System.out.println(Hello.class.getResource("."));
        System.out.println(Hello.class.getResource("/."));
        System.out.println(Hello.class.getClassLoader().getResource("."));

        System.out.println(ClassLoader.getSystemClassLoader().getResource(""));
        System.out.println(ClassLoader.getSystemClassLoader().getResource("."));

    }

}
