
package sps.rest.service;

import javax.persistence.EntityManager;
import javax.persistence.EntityManagerFactory;
import javax.servlet.http.HttpServletRequest;
import javax.ws.rs.Consumes;
import javax.ws.rs.GET;
import javax.ws.rs.POST;
import javax.ws.rs.PUT;
import javax.ws.rs.Path;
import javax.ws.rs.PathParam;
import javax.ws.rs.Produces;
import javax.ws.rs.QueryParam;
import javax.ws.rs.core.Context;
import javax.ws.rs.core.MediaType;
import javax.ws.rs.core.Response;

import sps.db.tables.Question;
import sps.db.tables.utils.PersistenceUtil;
import sps.db.tables.utils.QuestionUtil;
import sps.schema.generated.QuestionType;
import sps.schema.generated.SpsReply;
import sps.schema.generated.SpsRequest;
import sps.schema.utils.MappingUtils;

/** Example resource class hosted at the URI path "/myresource"
 */
@Path("/question")
public class QuestionResource {
	
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
	public QuestionResource() {
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

	@GET
	@Produces({MediaType.APPLICATION_XML, MediaType.APPLICATION_JSON})
	@Path("/{questionId}")
	public Response retrieveQuestionById(@PathParam("questionId") long questionId) {
		QuestionType questionType = null;
		QuestionUtil qu = new QuestionUtil(entityManager);

		//Find the user
		entityManager.clear();
		entityManager.getTransaction().begin();
		Question question = qu.findQuestion(questionId);
		//Map the user to a user reply type
		questionType = MappingUtils.mapQuestionToQuestionType(question);
		
		entityManager.getTransaction().commit();
		entityManager.close();
		//Add the user reply type to the reply
		getReply().getQuestionReply().add(questionType);
		

		return Response.ok(getReply()).build();
	}
	
	@PUT
	@Produces({MediaType.APPLICATION_XML, MediaType.APPLICATION_JSON})
	@Consumes({MediaType.APPLICATION_XML, MediaType.APPLICATION_JSON})
	@Path("/{questionId}")
	public Response updateQuestion(SpsRequest spsRequest, @PathParam("questionId") long questionId) throws Exception {
		QuestionType questionType = null;
		QuestionUtil qu = new QuestionUtil(entityManager);

		//Find the user
		entityManager.clear();
		entityManager.getTransaction().begin();
		Question question = qu.findQuestion(questionId);
		question = MappingUtils.mapQuestionTypeToQuestion(spsRequest.getQuestionRequest().getQuestion(), question);
		question = qu.updateQuestion(question);
		questionType = MappingUtils.mapQuestionToQuestionType(question);
		entityManager.getTransaction().commit();
		entityManager.close();
		//Add the user reply type to the reply
		getReply().getQuestionReply().add(questionType);
		

		return Response.ok(getReply()).build();
	}
	
	@POST
	@Produces({MediaType.APPLICATION_XML, MediaType.APPLICATION_JSON})
	@Consumes({MediaType.APPLICATION_XML, MediaType.APPLICATION_JSON})
	public Response addQuestion(SpsRequest spsRequest) {
		QuestionType questionType = null;
		QuestionUtil qu = new QuestionUtil(entityManager);

		//Find the user
		entityManager.clear();
		entityManager.getTransaction().begin();
		Question question = new Question();
		question = MappingUtils.mapQuestionTypeToQuestion(spsRequest.getQuestionRequest().getQuestion(), question);
		qu.addQuestion(question);
		questionType = MappingUtils.mapQuestionToQuestionType(question);
		entityManager.getTransaction().commit();
		entityManager.close();
		//Add the user reply type to the reply
		getReply().getQuestionReply().add(questionType);
		

		return Response.ok(getReply()).build();
	}
	
}
