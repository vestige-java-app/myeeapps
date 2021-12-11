package mypackage;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.io.PrintWriter;
import java.util.Arrays;

import javax.servlet.ServletException;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.apache.commons.codec.Decoder;
import org.apache.commons.io.IOUtils;
import org.apache.http.HttpEntity;
import org.apache.http.impl.client.HttpClientBuilder;

public class Hello extends HttpServlet {

    private static final long serialVersionUID = -8378070660296513701L;

    @SuppressWarnings("unchecked")
    @Override
    protected void doGet(final HttpServletRequest req, final HttpServletResponse resp) throws ServletException, IOException {
        PrintWriter writer = resp.getWriter();
        writer.println("hello war2");
        writer.println();
        
        for (Class<?> c : Arrays.asList(IOUtils.class, Decoder.class, HttpClientBuilder.class, HttpEntity.class)) {
            writer.println(c);
            writer.println(Hello.class.getClassLoader().getResource(c.getName().replace('.', '/').concat(".class")));
            ClassLoader classLoader = c.getClassLoader();
            writer.println(System.identityHashCode(classLoader) + " " + classLoader);
            writer.println();
        }
        InputStream inputStream = Hello.class.getResourceAsStream("myresource.txt");
        if (inputStream != null) {
            try {
                writer.println(new BufferedReader(new InputStreamReader(inputStream)).readLine());
            } finally {
                inputStream.close();
            }
        }
    }

}
