
package sps.rest.service;

import java.util.List;

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

import sps.db.tables.GameSession;
import sps.db.tables.Question;
import sps.db.tables.utils.GameSessionUtil;
import sps.db.tables.utils.PersistenceUtil;
import sps.db.tables.utils.QuestionUtil;
import sps.schema.generated.GameSessionType;
import sps.schema.generated.QuestionType;
import sps.schema.generated.SpsReply;
import sps.schema.generated.SpsRequest;
import sps.schema.utils.MappingUtils;


/** Example resource class hosted at the URI path "/myresource"
 */
@Path("/gamesession")
public class GameSessionResource {
	
	private EntityManagerFactory entityManagerFactory = PersistenceUtil.getEntityManagerFactory();
	private EntityManager entityManager;

	public static String ACTION_CREATE = "create";
	public static String ACTION_SEARCH = "search";

	@Context HttpServletRequest request;
	@QueryParam("showLinks") boolean showLinks;
	@QueryParam("action") String action;
	@QueryParam("showFull") boolean showFull;
	@QueryParam("showUsers") boolean showUsers;
	@QueryParam("showQuestions") boolean showQuestions;

	/**
	 * 
	 */
	public GameSessionResource() {
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
	@Path("/{gameSessionId}")
	public Response retrieveGameSessionById(@PathParam("gameSessionId") long gameSessionId) {
		GameSessionType gameSessionType = null;
		GameSessionUtil gsu = new GameSessionUtil(entityManager);

		//Find the user
		entityManager.clear();
		entityManager.getTransaction().begin();
		GameSession gameSession = gsu.findGameSession(gameSessionId);
		//Map the user to a user reply type
		gameSessionType = MappingUtils.mapGameSessionToGameSessionType(gameSession);
		gameSessionType = doOptionalMappings(gameSession, gameSessionType);
		
		entityManager.getTransaction().commit();
		entityManager.close();
		//Add the user reply type to the reply
		getReply().getGameSessionReply().add(gameSessionType);
		

		return Response.ok(getReply()).build();
	}
	
	private GameSessionType doOptionalMappings(GameSession gameSession, GameSessionType gameSessionType) {
		if(showFull || showUsers) {
			gameSessionType = MappingUtils.mapUsersToGameSessionType(gameSession, gameSessionType);
		}
		if(showFull || showQuestions) {
			gameSessionType = MappingUtils.mapUsersToGameSessionType(gameSession, gameSessionType);
		}
		return gameSessionType;
	}
	
	@PUT
	@Produces({MediaType.APPLICATION_XML, MediaType.APPLICATION_JSON})
	@Consumes({MediaType.APPLICATION_XML, MediaType.APPLICATION_JSON})
	@Path("/{gameSessionId}")
	public Response updateGameSession(SpsRequest spsRequest, @PathParam("gameSessionId") long gameSessionId) throws Exception {
		GameSessionType gameSessionType = null;
		GameSessionUtil gsu = new GameSessionUtil(entityManager);

		//Find the user
		entityManager.clear();
		entityManager.getTransaction().begin();
		GameSession gameSession = gsu.findGameSession(gameSessionId);
		gameSession = MappingUtils.mapGameSessionTypeToGameSession(spsRequest.getGameSessionRequest().getGameSession(),gameSession);
		gameSession = gsu.updateGameSession(gameSession);
		gameSessionType = MappingUtils.mapGameSessionToGameSessionType(gameSession);
		gameSessionType = doOptionalMappings(gameSession, gameSessionType);

		entityManager.getTransaction().commit();
		entityManager.close();
		//Add the user reply type to the reply
		getReply().getGameSessionReply().add(gameSessionType);
		

		return Response.ok(getReply()).build();
	}
	
	@POST
	@Produces({MediaType.APPLICATION_XML, MediaType.APPLICATION_JSON})
	@Consumes({MediaType.APPLICATION_XML, MediaType.APPLICATION_JSON})
	public Response addGameSession(SpsRequest spsRequest) {
		GameSessionType gameSessionType = null;
		GameSessionUtil gsu = new GameSessionUtil(entityManager);

		//Find the user
		entityManager.clear();
		entityManager.getTransaction().begin();
		GameSession gameSession = new GameSession();
		gameSession = MappingUtils.mapGameSessionTypeToGameSession(spsRequest.getGameSessionRequest().getGameSession(), gameSession);
		gsu.addGameSession(gameSession);
		gameSessionType = MappingUtils.mapGameSessionToGameSessionType(gameSession);
		gameSessionType = doOptionalMappings(gameSession, gameSessionType);

		entityManager.getTransaction().commit();
		entityManager.close();
		//Add the user reply type to the reply
		getReply().getGameSessionReply().add(gameSessionType);
		

		return Response.ok(getReply()).build();
	}

	@POST
	@Produces({MediaType.APPLICATION_XML, MediaType.APPLICATION_JSON})
	@Consumes({MediaType.APPLICATION_XML, MediaType.APPLICATION_JSON})
	@Path("/{gameSessionId}/question")
	public Response addQuestionToGameSession(SpsRequest spsRequest, @PathParam("gameSessionId") long gameSessionId) {
		GameSessionType gameSessionType = null;
		GameSession gameSession=null;
		GameSessionUtil gsu = new GameSessionUtil(entityManager);
		QuestionUtil qu = new QuestionUtil(entityManager);
		List<QuestionType> questionTypes = spsRequest.getGameSessionRequest().getGameSession().getQuestion();
		
		//Find the user
		entityManager.clear();
		entityManager.getTransaction().begin();
		gameSession = gsu.findGameSession(gameSessionId);
		gameSession = MappingUtils.mapGameSessionTypeToGameSession(spsRequest.getGameSessionRequest().getGameSession(), gameSession);
		Question question = null;
		for(QuestionType questionType : questionTypes) {
			if(questionType.getId() == 0) {
				question = new Question();				
				question = qu.addQuestion(question);
			} else
			{
				question = qu.findQuestion(questionType.getId());
			}			
			question = MappingUtils.mapQuestionTypeToQuestion(questionType, question);
			gsu.addGameSessionQuestion(gameSession, question);
		}
	
		gameSessionType = MappingUtils.mapGameSessionToGameSessionType(gameSession);
		gameSessionType = doOptionalMappings(gameSession, gameSessionType);

		entityManager.getTransaction().commit();
		entityManager.close();
		//Add the user reply type to the reply
		getReply().getGameSessionReply().add(gameSessionType);
		

		return Response.ok(getReply()).build();
	}	
	
	
	
}
