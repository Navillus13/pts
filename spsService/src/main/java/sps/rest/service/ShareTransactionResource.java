
package sps.rest.service;

import javax.persistence.EntityManager;
import javax.persistence.EntityManagerFactory;
import javax.servlet.http.HttpServletRequest;
import javax.ws.rs.GET;
import javax.ws.rs.Path;
import javax.ws.rs.PathParam;
import javax.ws.rs.Produces;
import javax.ws.rs.QueryParam;
import javax.ws.rs.core.Context;
import javax.ws.rs.core.MediaType;
import javax.ws.rs.core.Response;

import sps.db.tables.ShareTransaction;
import sps.db.tables.utils.PersistenceUtil;
import sps.db.tables.utils.ShareTransactionUtil;
import sps.schema.generated.ShareTransactionType;
import sps.schema.generated.SpsReply;
import sps.schema.generated.UserReplyType;
import sps.schema.utils.MappingUtils;

/** Example resource class hosted at the URI path "/myresource"
 */
@Path("/transaction")
public class ShareTransactionResource {
	
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
	public ShareTransactionResource() {
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

	/**
	 * Retrieve the user by user ID
	 * @param userId
	 * @return
	 */
	@GET
	@Produces({MediaType.APPLICATION_XML, MediaType.APPLICATION_JSON})
	@Path("/{transactionId}")
	public Response retrieveTransactionById(@PathParam("transactionId") long transactionId) {
		UserReplyType userReplyType = MappingUtils.getObjectFactory().createUserReplyType();
		ShareTransactionUtil su = new ShareTransactionUtil(entityManager);
		
		//Find the user
		entityManager.clear();
		entityManager.getTransaction().begin();
		ShareTransaction share = su.findShareTransaction(transactionId);
		//Map the user to a user reply type
		ShareTransactionType shareType = MappingUtils.mapShareTransToShareTransType(share);
		entityManager.getTransaction().commit();
		entityManager.close();
		//Add the user reply type to the reply
		userReplyType.getShareTransactions().add(shareType);
		getReply().getUserReply().add(userReplyType);
		

		return Response.ok(getReply()).build();
	}
	
}
