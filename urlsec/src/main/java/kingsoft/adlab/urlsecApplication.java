package kingsoft.adlab;

import org.glassfish.grizzly.http.server.HttpServer;
import org.glassfish.jersey.grizzly2.httpserver.GrizzlyHttpServerFactory;
import org.glassfish.jersey.server.ResourceConfig;

import javax.json.stream.JsonGenerator;
import javax.ws.rs.ApplicationPath;
import java.io.IOException;
import java.net.URI;

/**
 * urlsecApplication class.
 *
 */
@ApplicationPath("/")
public class urlsecApplication extends ResourceConfig{
    // Base URI the Grizzly HTTP server will listen on
    public static final String BASE_URI = "http://0.0.0.0/";


    /**
     * Starts Grizzly HTTP server exposing JAX-RS resources defined in this application.
     * @return Grizzly HTTP server.
     */
    public static HttpServer startServer() {
        // create a resource config that scans for JAX-RS resources and providers
        // in kingsoft.adlab package
        final ResourceConfig rc = new ResourceConfig().packages("kingsoft.adlab");
        rc.property(JsonGenerator.PRETTY_PRINTING, true);

        // create and start a new instance of grizzly http server
        // exposing the Jersey application at BASE_URI
        return GrizzlyHttpServerFactory.createHttpServer(URI.create(BASE_URI), rc);
    }

    /**
     * urlsecApplication method.
     * @param args
     * @throws IOException
     */
    public static void main(String[] args) throws IOException {

        final HttpServer server = startServer();
        System.out.println(String.format("Jersey app started with WADL available at "
                + "%sapplication.wadl\nHit enter to stop it...", BASE_URI));
        System.in.read();
        server.shutdown();
    }

   public urlsecApplication() {
       register(UrlSrcResource.class);
   }
}

