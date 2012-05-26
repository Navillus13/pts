
package sps.rest.service;

import javax.persistence.EntityManager;
import javax.persistence.EntityManagerFactory;
import javax.servlet.http.HttpServletRequest;
import javax.ws.rs.Consumes;
import javax.ws.rs.POST;
import javax.ws.rs.Path;
import javax.ws.rs.Produces;
import javax.ws.rs.QueryParam;
import javax.ws.rs.core.Context;
import javax.ws.rs.core.MediaType;
import javax.ws.rs.core.Response;

import sps.db.tables.utils.PersistenceUtil;
import sps.schema.generated.SpsReply;
import sps.schema.generated.SpsRequest;

/** Example resource class hosted at the URI path "/myresource"
 */
@Path("/util")
public class UtilitiesResource {
	
	private EntityManagerFactory entityManagerFactory = PersistenceUtil.getEntityManagerFactory();
	private EntityManager entityManager;

	public static String ACTION_CREATE = "create";
	public static String ACTION_SEARCH = "search";

	@Context HttpServletRequest request;
	@QueryParam("showLinks") boolean showLinks;
	@QueryParam("action") String action;

	/**
	 * 
	 */
	public UtilitiesResource() {
		super();
		entityManager = entityManagerFactory.createEntityManager();
	}

	private SpsReply reply;

	public SpsReply getReply() {
		if(reply == null) {
			reply = new SpsReply();
		}
		return reply;
	}

	@POST
	@Path("/xmltojson")
	@Consumes({MediaType.APPLICATION_XML})
	@Produces({MediaType.APPLICATION_JSON})
	public Response getJSONFromXML(SpsRequest fullRequest) {
		return Response.ok(fullRequest).build();
	}

}
