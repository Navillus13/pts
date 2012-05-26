package sps.rest.service;

import java.util.List;

import javax.persistence.EntityManager;
import javax.persistence.EntityManagerFactory;
import javax.servlet.http.HttpServletRequest;
import javax.ws.rs.Consumes;
import javax.ws.rs.DELETE;
import javax.ws.rs.FormParam;
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

import sps.db.tables.Answer;
import sps.db.tables.GameSession;
import sps.db.tables.GameSessionUser;
import sps.db.tables.Question;
import sps.db.tables.ShareTransaction;
import sps.db.tables.Users;
import sps.db.tables.utils.AnswerUtil;
import sps.db.tables.utils.GameSessionUtil;
import sps.db.tables.utils.PersistenceUtil;
import sps.db.tables.utils.QuestionUtil;
import sps.db.tables.utils.ShareTransactionUtil;
import sps.db.tables.utils.UsersUtil;
import sps.schema.generated.AnswerType;
import sps.schema.generated.GameSessionType;
import sps.schema.generated.SpsReply;
import sps.schema.generated.SpsRequest;
import sps.schema.generated.UserReplyType;
import sps.schema.generated.UserRequestType;
import sps.schema.utils.MappingUtils;

/**
 * Example resource class hosted at the URI path "/myresource"
 */
@Path("/user")
public class UserResource {

	private EntityManagerFactory entityManagerFactory = PersistenceUtil
			.getEntityManagerFactory();
	private EntityManager entityManager;

	public static String ACTION_CREATE = "create";
	public static String ACTION_SEARCH = "search";

	@Context
	HttpServletRequest request;
	@QueryParam("showLinks")
	boolean showLinks;
	@QueryParam("action")
	String action;
	@QueryParam("showFull")
	boolean showFull;
	@QueryParam("showGS")
	boolean showGameSessions;
	@QueryParam("showQuestions")
	boolean showQuestions;
	@QueryParam("showTransactions")
	boolean showTransactions;

	/**
	 * 
	 */
	public UserResource() {
		super();
		entityManager = entityManagerFactory.createEntityManager();
	}

	private SpsReply reply;

	public SpsReply getReply() {
		if (reply == null) {
			reply = new SpsReply();
		}
		return reply;
	}

	/**
	 * Retrieve the user by user ID
	 * 
	 * @param userId
	 * @return
	 */
	@GET
	@Produces({ MediaType.APPLICATION_XML, MediaType.APPLICATION_JSON })
	@Path("/{userId}")
	public Response retrieveUserById(@PathParam("userId") long userId) {
		UserReplyType userReplyType = null;
		UsersUtil uu = new UsersUtil(entityManager);

		// Find the user
		entityManager.clear();
		entityManager.getTransaction().begin();
		Users user = uu.findUser(userId);
		// Map the user to a user reply type
		userReplyType = MappingUtils.mapUserToUserReply(userReplyType, user,
				request.getRequestURI(), request.getSession().getId());
		userReplyType = doOptionalMappings(user, userReplyType);
		entityManager.getTransaction().commit();
		entityManager.close();
		// Add the user reply type to the reply
		getReply().getUserReply().add(userReplyType);

		return Response.ok(getReply()).build();
	}

	private UserReplyType doOptionalMappings(Users user,
			UserReplyType userReplyType) {
		GameSessionType gameSessionType = null;
		if (showFull || showTransactions) {
			userReplyType = MappingUtils.mapTransactionsToUserReply(user,
					userReplyType);
		}
		if (showFull || showGameSessions) {
			userReplyType = MappingUtils.mapGameSessionsToUserReply(user,
					userReplyType);
		}
		if ((showFull || showQuestions)
				&& userReplyType.getGameSessions() != null
				&& userReplyType.getGameSessions().size() > 0) {
			gameSessionType = userReplyType.getGameSessions().get(0);
			for (GameSessionUser gameSessionUser : user.getGameSessionUsers()) {
				gameSessionType = MappingUtils.mapAnswerToGameSessionType(
						gameSessionUser, gameSessionType);
			}

		}
		return userReplyType;
	}

	/**
	 * Retrieve user from email
	 * 
	 * @param email
	 * @return
	 */
	private Response retrieveUserByEmail(String email) {
		UserReplyType userReplyType = null;
		UsersUtil uu = new UsersUtil(entityManager);
		entityManager.clear();
		entityManager.getTransaction().begin();

		// Find the user
		Users user = uu.findUser(email);
		// Map the user to a user reply type
		userReplyType = MappingUtils.mapUserToUserReply(userReplyType, user,
				request.getRequestURI(), request.getSession().getId());
		userReplyType = doOptionalMappings(user, userReplyType);

		entityManager.getTransaction().commit();
		entityManager.close();
		// Add the user reply type to the reply
		getReply().getUserReply().add(userReplyType);

		return Response.ok(getReply()).build();
	}

	@GET
	@Produces({ MediaType.APPLICATION_XML, MediaType.APPLICATION_JSON })
	@Path("/{userId}/gameSession/{gameSessionId}")
	public Response retrieveGameSessionByUserAndId(
			@PathParam("userId") long userId,
			@PathParam("gameSessionId") long gameSessionId) {
		UserReplyType userReplyType = null;
		UsersUtil uu = new UsersUtil(entityManager);
		GameSessionUtil gsu = new GameSessionUtil(entityManager);
		AnswerUtil au = new AnswerUtil(entityManager);

		// Find the user
		entityManager.clear();
		entityManager.getTransaction().begin();
		Users user = uu.findUser(userId);
		GameSessionUser gameSessionUser = gsu.findGameSessionUserByIds(userId,
				gameSessionId);
		if (gameSessionUser != null) {
			// Map the user to a user reply type
			userReplyType = MappingUtils
					.mapUserToUserReply(userReplyType, user, request
							.getRequestURI(), request.getSession().getId());
			// Map the Game Session type
			GameSessionType gameSessionType = MappingUtils
					.mapGameSessionToGameSessionType(gameSessionUser
							.getGameSession());
			List<Answer> answers = au
					.findAnswersByGameSessionUser(gameSessionUser);
			for (Answer answer : answers) {
				gameSessionType = MappingUtils.mapAnswerToGameSessionType(
						gameSessionUser, gameSessionType);
			}
			userReplyType.getGameSessions().add(gameSessionType);
		}
		userReplyType = doOptionalMappings(user, userReplyType);

		entityManager.getTransaction().commit();
		entityManager.close();
		// Add the user reply type to the reply
		getReply().getUserReply().add(userReplyType);

		return Response.ok(getReply()).build();
	}

	/**
	 * Retrieve transactions by user
	 * 
	 * @param userId
	 * @return
	 */
	@GET
	@Produces({ MediaType.APPLICATION_XML, MediaType.APPLICATION_JSON })
	@Path("/{userId}/transactions")
	public Response retrieveTransactions(@PathParam("userId") long userId) {
		UserReplyType userReplyType = null;
		UsersUtil uu = new UsersUtil(entityManager);

		entityManager.clear();
		entityManager.getTransaction().begin();
		Users user = uu.findUser(userId);

		// Map the user to a user reply type
		userReplyType = MappingUtils.mapUserToUserReply(userReplyType, user,
				request.getRequestURI(), request.getSession().getId());
		userReplyType = MappingUtils.mapTransactionsToUserReply(user,
				userReplyType);
		userReplyType = doOptionalMappings(user, userReplyType);
		entityManager.getTransaction().commit();
		entityManager.close();
		// Add the user reply type to the reply
		getReply().getUserReply().add(userReplyType);

		return Response.ok(getReply()).build();
	}

	/**
	 * Either Search or Create a user
	 * 
	 * @param fullRequest
	 * @return
	 */
	@POST
	@Consumes({ MediaType.APPLICATION_JSON, MediaType.APPLICATION_XML })
	@Produces({ MediaType.APPLICATION_XML, MediaType.APPLICATION_JSON })
	public Response postUser(SpsRequest fullRequest) {
		UserRequestType userRequest = null;
		UserReplyType userReply = null;
		UsersUtil uu = new UsersUtil(entityManager);

		userRequest = fullRequest.getUserRequest();

		// If no action coming in from xml, check query string
		if (userRequest.getAction() != null) {
			action = userRequest.getAction();
		}

		if (action == null || action.length() == 0) {
			return Response.status(406)
					.entity("Query Parameter Action is null and should not be")
					.build();
		}

		if (action.equalsIgnoreCase(ACTION_CREATE)) {
			entityManager.clear();
			entityManager.getTransaction().begin();
			Users user = new Users();
			user = MappingUtils.mapUserTypeToUser(userRequest.getUser(), user);
			user = uu.addUser(user);
			userReply = MappingUtils.mapUserToUserReply(userReply, user,
					request.getRequestURI(), request.getSession().getId());
			userReply = doOptionalMappings(user, userReply);

			entityManager.getTransaction().commit();
			entityManager.close();
			getReply().getUserReply().add(userReply);
			return Response.ok(getReply()).build();
		} else if (action.equalsIgnoreCase(ACTION_SEARCH)) {

			// Search by ID
			if (userRequest.getUser() != null
					&& userRequest.getUser().getId() > 0) {
				return retrieveUserById(userRequest.getUser().getId());
			} else if (userRequest.getUser() != null
					&& userRequest.getUser().getEmail() != null
					&& userRequest.getUser().getEmail().length() > 0) {
				return retrieveUserByEmail(userRequest.getUser().getEmail());
			} else {
				return Response
						.status(404)
						.entity("User could not be obtained from information provided")
						.build();
			}
		}

		return Response
				.status(406)
				.entity("Action: " + action
						+ " is not a vlid action besed on incoming request")
				.build();

	}

	/**
	 * Either search or create a transaction
	 * 
	 * @param fullRequest
	 * @param userId
	 * @return
	 */
	@POST
	@Consumes({ MediaType.APPLICATION_XML, MediaType.APPLICATION_JSON })
	@Produces({ MediaType.APPLICATION_XML, MediaType.APPLICATION_JSON })
	@Path("/{userId}/transactions")
	public Response postTransaction(SpsRequest fullRequest,
			@PathParam("userId") long userId) {
		UserRequestType userRequest = null;
		UserReplyType userReplyType = null;
		UsersUtil uu = new UsersUtil(entityManager);
		ShareTransactionUtil su = new ShareTransactionUtil(entityManager);

		userRequest = fullRequest.getUserRequest();

		action = userRequest.getAction();

		if (action == null) {
			return Response.status(406)
					.entity("Query Parameter Action is null and should not be")
					.entity(fullRequest).build();
		}

		if (action.equalsIgnoreCase(ACTION_CREATE)) {
			entityManager.clear();
			entityManager.getTransaction().begin();
			Users user = uu.findUser(userId);
			ShareTransaction transaction = MappingUtils
					.mapTransactionTypeToTransaction(
							userRequest.getShareTransaction(), user, null);
			transaction = su.startShareTransaction(transaction);
			userReplyType = MappingUtils
					.mapUserToUserReply(userReplyType, user, request
							.getRequestURI(), request.getSession().getId());
			userReplyType = doOptionalMappings(user, userReplyType);
			userReplyType.getShareTransactions().add(
					MappingUtils.mapShareTransToShareTransType(transaction));
			entityManager.getTransaction().commit();
			entityManager.close();
			getReply().getUserReply().add(userReplyType);

			return Response.ok(getReply()).build();
		} else if (action.equalsIgnoreCase(ACTION_SEARCH)) {
			// TODO: Add Searches

		}

		return Response
				.status(406)
				.entity("Action: " + action
						+ " is not a valid action besed on incoming request")
				.build();

	}

	@POST
	@Produces({ MediaType.APPLICATION_XML, MediaType.APPLICATION_JSON })
	@Consumes({ MediaType.APPLICATION_XML, MediaType.APPLICATION_JSON })
	@Path("/{userId}/gamesession/{gameSessionId}/question/{questionId}/answer/")
	public Response addAnswerToGameSession(SpsRequest spsRequest,
			@PathParam("userId") long userId,
			@PathParam("gameSessionId") long gameSessionId,
			@PathParam("questionId") long questionId) {
		GameSessionUser gameSessionUser = null;
		GameSession gameSession = null;
		GameSessionType gameSessionType;
		GameSessionUtil gsu = new GameSessionUtil(entityManager);
		QuestionUtil qu = new QuestionUtil(entityManager);
		AnswerUtil au = new AnswerUtil(entityManager);
		AnswerType aType = spsRequest.getAnswerRequest().getAnswer();

		// Find the user
		entityManager.clear();
		entityManager.getTransaction().begin();
		gameSessionUser = gsu.findGameSessionUserByIds(userId, gameSessionId);
		gameSession = gameSessionUser.getGameSession();
		Question question = qu.findQuestion(questionId);
		Answer answer = new Answer();
		answer = MappingUtils.mapAnswerTypeToAnswer(aType, answer);
		answer = au.addAnswer(answer, question, gameSessionUser);
		gameSessionType = MappingUtils
				.mapGameSessionToGameSessionType(gameSession);
		entityManager.refresh(gameSessionUser);
		gameSessionType = MappingUtils.mapAnswerToGameSessionType(
				gameSessionUser, gameSessionType);
		entityManager.getTransaction().commit();
		entityManager.close();
		// Add the user reply type to the reply
		getReply().getGameSessionReply().add(gameSessionType);

		return Response.ok(getReply()).build();
	}

	@PUT
	@Produces({MediaType.APPLICATION_XML, MediaType.APPLICATION_JSON})
	@Consumes({MediaType.APPLICATION_XML, MediaType.APPLICATION_JSON})
	@Path("/{userId}/gamesession/{gameSessionId}/question/{questionId}/answer/{answerId}")
	public Response updateAnswerToGameSession(SpsRequest spsRequest, @PathParam("userId") long userId, @PathParam("gameSessionId") long gameSessionId, @PathParam("questionId") long questionId, @PathParam("answerId") long answerId) {
		GameSessionUser gameSessionUser = null;
		GameSession gameSession=null;
		GameSessionType gameSessionType;
		GameSessionUtil gsu = new GameSessionUtil(entityManager);
		QuestionUtil qu = new QuestionUtil(entityManager);
		AnswerUtil au = new AnswerUtil(entityManager);
		AnswerType aType = spsRequest.getAnswerRequest().getAnswer();

		//Find the user
		entityManager.clear();
		entityManager.getTransaction().begin();
		gameSessionUser = gsu.findGameSessionUserByIds(userId, gameSessionId);
		gameSession = gameSessionUser.getGameSession();
		Question question = qu.findQuestion(questionId);
		Answer answer = au.findAnswer(answerId);
		answer = MappingUtils.mapAnswerTypeToAnswer(aType, answer);	
		answer = au.addAnswer(answer, question, gameSessionUser);	
		gameSessionType = MappingUtils.mapGameSessionToGameSessionType(gameSession);
		entityManager.refresh(gameSessionUser);
		gameSessionType = MappingUtils.mapAnswerToGameSessionType(gameSessionUser, gameSessionType);
		entityManager.getTransaction().commit();
		entityManager.close();
		//Add the user reply type to the reply
		getReply().getGameSessionReply().add(gameSessionType);


		return Response.ok(getReply()).build();
	}	
	
	
	/**
	 * Update a user
	 * 
	 * @param userId
	 * @param email
	 * @param password
	 * @return
	 */
	@PUT
	@Produces({ MediaType.APPLICATION_XML, MediaType.APPLICATION_JSON })
	@Path("/{userId}")
	public Response updateUser(@PathParam("userId") long userId,
			@FormParam("email") String email,
			@FormParam("password") String password) {
		UserReplyType userReplyType = null;
		UsersUtil uu = new UsersUtil(entityManager);
		Users user = null;

		user = uu.updateUser(userId, email, password);

		userReplyType = MappingUtils.mapUserToUserReply(userReplyType, user,
				request.getRequestURI(), request.getSession().getId());

		userReplyType = doOptionalMappings(user, userReplyType);

		getReply().getUserReply().add(userReplyType);

		return Response.ok(getReply()).build();
	}

	@PUT
	@Consumes({ MediaType.APPLICATION_XML, MediaType.APPLICATION_JSON })
	@Produces({ MediaType.APPLICATION_XML, MediaType.APPLICATION_JSON })
	@Path("/{userId}")
	public Response updateUser(SpsRequest fullRequest,
			@PathParam("userId") long userId) {
		UserReplyType userReplyType = null;
		UsersUtil uu = new UsersUtil(entityManager);
		entityManager.clear();
		entityManager.getTransaction().begin();
		Users user = entityManager.find(Users.class, userId);

		user = MappingUtils.mapUserTypeToUser(fullRequest.getUserRequest()
				.getUser(), user);

		user = uu.updateUser(user);

		userReplyType = MappingUtils.mapUserToUserReply(userReplyType, user,
				request.getRequestURI(), request.getSession().getId());
		userReplyType = doOptionalMappings(user, userReplyType);

		entityManager.getTransaction().commit();
		entityManager.close();

		getReply().getUserReply().add(userReplyType);

		return Response.ok(getReply()).build();
	}

	@DELETE
	@Produces({ MediaType.APPLICATION_XML, MediaType.APPLICATION_JSON })
	@Path("/{userId}")
	public Response logoutUser(@PathParam("userId") long userId) {
		UsersUtil uu = new UsersUtil(entityManager);

		uu.logout(userId, request.getSession().getId());

		return Response.ok().build();
	}

}
