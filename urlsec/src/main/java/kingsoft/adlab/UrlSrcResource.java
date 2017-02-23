package kingsoft.adlab;


import com.google.gson.Gson;
import com.google.gson.GsonBuilder;
import com.google.gson.JsonParser;
import udp_model.UdpUrlSec;
import udp_model.UrlInfo;

import javax.annotation.security.PermitAll;
import javax.json.*;
import javax.validation.constraints.Null;
import javax.ws.rs.*;
import javax.ws.rs.core.MediaType;
import javax.ws.rs.core.Response.Status;

import javax.ws.rs.core.Response;

/**
 * Created by vici on 09/02/2017.
 */
@Path("/")
public class UrlSrcResource {

    @Path("/test")
    @GET
    @Produces(MediaType.TEXT_PLAIN)
    public Response getTutorial () {


        Response response = Response.status(Status.OK).entity("test").build();
        return response;
    }

    @GET
    @Produces(MediaType.TEXT_PLAIN)
    public Response get () {
        Response response = Response.status(Status.OK).entity(
                "GET /query?url=  HTTP/1.1\n" +
                "   - get urlinfo   \n" +
                "   - Return jsonObject msg\n" +
                "       -url        |   the url entered to query. \n" +
                "       -status        |   status status of query. \n"+
                "       -urltype    |   Type of risk.\n"+
                "       -urltype_ch |   风险类型.\n"+
                "       -eviltype   |   Malicious classification.\n"+
                "       -eviltype_ch|   恶意分类\n"
                ).build();
        return response;
    }

    @GET
    @Path("query")
    @Produces(MediaType.APPLICATION_JSON)
    public Response geturlinfo (@QueryParam("url") String url) {
        JsonObjectBuilder jsonbuilder = Json.createObjectBuilder();
        jsonbuilder.add("error", JsonValue.NULL);
        Status status = Status.OK;
        try{

            UrlInfo ui = UdpUrlSec.getSec(url);
            jsonbuilder.add("url", ui.getUrl());
            jsonbuilder.add("status", ui.getStatus());
            jsonbuilder.add("urltype", ui.getUrltype());
            jsonbuilder.add("urltype_ch", ui.getUrlTypeCH());

            JsonArrayBuilder eviltypes = Json.createArrayBuilder();
            for (int evil: ui.getEviltype()){
                eviltypes.add(evil);
            }
            jsonbuilder.add("eviltype", eviltypes);


            JsonArrayBuilder eviltypes_ch = Json.createArrayBuilder();
            for (String evil_ch: ui.getEvilTypeCH()){
                eviltypes_ch.add(evil_ch);
            }
            jsonbuilder.add("eviltype_ch", eviltypes_ch);


        }catch (Exception ex){
            jsonbuilder.add("error", "Bad request.");
            status = Status.BAD_REQUEST;


        }finally {
            String simple_json = jsonbuilder.build().toString();
            String pretty_json = crunchifyPrettyJSONUtility(simple_json);
            return  Response.status(status).entity(pretty_json).build();

        }

    }

    // Prettify JSON Utility
    private static String crunchifyPrettyJSONUtility(String simpleJSON) {

        JsonParser crunhifyParser = new JsonParser();
        com.google.gson.JsonObject json = crunhifyParser.parse(simpleJSON).getAsJsonObject();

        Gson prettyGson = new GsonBuilder().setPrettyPrinting().create();
        return prettyGson.toJson(json);

    }

}
