package sps.schema.utils;

import java.math.BigDecimal;
import java.util.Set;

import sps.db.tables.Answer;
import sps.db.tables.GameSession;
import sps.db.tables.GameSessionQuestion;
import sps.db.tables.GameSessionUser;
import sps.db.tables.Question;
import sps.db.tables.ShareTransaction;
import sps.db.tables.Users;
import sps.schema.generated.AddressType;
import sps.schema.generated.AnswerType;
import sps.schema.generated.GameSessionType;
import sps.schema.generated.ObjectFactory;
import sps.schema.generated.QuestionType;
import sps.schema.generated.ShareTransactionType;
import sps.schema.generated.UserReplyType;
import sps.schema.generated.UserType;


public abstract class MappingUtils {
	
	private static ObjectFactory objectFactory;
	public static ObjectFactory getObjectFactory() {
		if(objectFactory == null) {
			objectFactory = new ObjectFactory();
		}
		return objectFactory;
	}
	
	/**
	 * 
	 * @param userReply - if null a new one will be created, if not null the given userReplyType
	 * 					  will be updated and the user node completely replaced 
	 * @param user - hibernate user object
	 * @param requestURI - requestURI from requester
	 * @param sessionId - sessiondID
	 * @param showAddress - true to build address into response
	 * @param showDates - true to build dates such as createDate and lastLoginDate
	 * @return userReply 
	 */
    public static UserReplyType mapUserToUserReply(UserReplyType userReply, Users user, String requestURI, String sessionId, boolean mapAddress, boolean mapDates, boolean mapTransactions, boolean mapGameSessions) {
		Set<ShareTransaction> transactions = null;
		Set<GameSessionUser> gameSessions = null;
    	if(userReply==null) {
    		userReply = getObjectFactory().createUserReplyType();
    	}    	
   		
    	//Map User Type
    	userReply.setUser(mapUserToUserType(user, mapAddress, mapDates));
		
    	//Map Transactions Type
    	if(mapTransactions) {
    		transactions = user.getShareTransactions();
		
    		for(ShareTransaction transaction : transactions) {
    			ShareTransactionType transactionType = mapShareTransToShareTransType(transaction);
    			userReply.getShareTransactions().add(transactionType);
    		}
    	}
    	if(mapGameSessions) {
    		gameSessions = user.getGameSessionUsers();
    		
    		for(GameSessionUser gameSessionUser : gameSessions) {
    			GameSessionType gameSessionType = mapGameSessionToGameSessionType(gameSessionUser.getGameSession(), false, false);
    			userReply.getGameSessions().add(gameSessionType);
    		}
    	}
   	
    	return userReply;
    	
    }
    
    public static QuestionType mapQuestionToQuestionType(Question question) {
    	if(question != null) {
    		QuestionType qType = getObjectFactory().createQuestionType();
    		qType.setId(question.getId());
    		qType.setCorrectAnswer(question.getCorrectAnswer());
    		qType.setAnswerType(question.getAnswerType());
    		qType.setCategory(question.getCategory());
    		qType.setDefaultAnswer(question.getDefaultAnswer());
    		qType.setQuestionText(question.getQuestionText());
    		return qType;
    	} else {
    		return null;
    	}
    }
    
    public static GameSessionType mapAnswerToGameSessionType(Answer answer, GameSessionType gType) {
    	if(answer != null) {
    		QuestionType qType = mapQuestionToQuestionType(answer.getQuestion());
    		qType.setAnswer(mapAnswerToAnswerType(answer));
    		gType.getQuestion().add(qType);
    	}
    	return gType;
    }
    
    public static GameSessionType mapQuestionsToGameSessionType(GameSession gameSession, GameSessionType gType) {
    	Set<GameSessionQuestion> gameSessionQuestions = gameSession.getGameSessionQuestions();
    	if(gameSessionQuestions != null) {
    		for(GameSessionQuestion gameSessionQuestion : gameSessionQuestions) {
    			gType.getQuestion().add(MappingUtils.mapQuestionToQuestionType(gameSessionQuestion.getQuestion()));    				
    		}
    	}
    	
    	return gType;
    }
    
    public static GameSessionType mapUsersToGameSessionType(GameSession gameSession, GameSessionType gType) {
    	Set<GameSessionUser> gameSessionUsers = gameSession.getGameSessionUsers();
    	if(gameSessionUsers != null) {
    		for(GameSessionUser gameSessionUser: gameSessionUsers) {
    			gType.getUser().add(MappingUtils.mapUserToUserType(gameSessionUser.getUser(), false, false));
    		}
    	}
    	return gType;
    }
    
    
    
    public static GameSessionType mapGameSessionToGameSessionType(GameSession gameSession, boolean mapUsers, boolean mapQuestions) {
    	if(gameSession != null) {
    		GameSessionType gType = getObjectFactory().createGameSessionType();
    		gType.setId(gameSession.getId());
    		gType.setMaxUsers(gameSession.getMaxUsers());
    		gType.setCategory(gameSession.getCategory());
    		gType.setDescription(gameSession.getDescription());
    		gType.setStatus("Not Started");
    		if(mapUsers) {
    			gType = mapUsersToGameSessionType(gameSession, gType);
    		}
    		if(mapQuestions) {
    			gType = mapQuestionsToGameSessionType(gameSession, gType);
    		}
    		return gType;
    	} else {
    		return null;
    	}
    }
    
    public static GameSession mapGameSessionTypeToGameSession(GameSessionType gameSessionType, GameSession gameSession) {
    	if(gameSessionType.getMaxUsers() != null) {
    		gameSession.setMaxUsers(gameSessionType.getMaxUsers());
    	}
    	if(gameSessionType.getCategory() != null) {
    		gameSession.setCategory(gameSessionType.getCategory());
    	}
    	if(gameSessionType.getDescription() != null) {
    		gameSession.setDescription(gameSession.getDescription());
    	}
    	return gameSession;
    }
    
    public static Question mapQuestionTypeToQuestion(QuestionType questionType, Question question) {
    	if(questionType.getCorrectAnswer() != null) {
    		question.setCorrectAnswer(questionType.getCorrectAnswer());
    	}
    	if(questionType.getAnswerType() != null) {
        	question.setAnswerType(questionType.getAnswerType());
    	}
    	if(questionType.getCategory() != null) {
    		question.setCategory(questionType.getCategory());
    	}
    	if(questionType.getDefaultAnswer() != null) {
    		question.setDefaultAnswer(questionType.getDefaultAnswer());
    	}
    	if(questionType.getQuestionText() != null) {
    		question.setQuestionText(questionType.getQuestionText());
    	}
    	return question;
    }
    
    public static UserType mapUserToUserType(Users user, boolean mapAddress, boolean mapDates) {
    	
    	if(user != null) {
    		UserType userType = getObjectFactory().createUserType();
    		
    		userType.setFirstName(user.getFirstName());
    		userType.setLastName(user.getLastName());
    		userType.setEmail(user.getEmail());
    		if(user.getId() != null) {
    			userType.setId(user.getId());
    		}
    		
    		if(mapAddress) {
    			AddressType billingAddress = getObjectFactory().createAddressType();
    			billingAddress.setAddressLine1(user.getBillingAddress1());
    			billingAddress.setAddressLine2(user.getBillingAddress2());
    			billingAddress.setAddressLine3(user.getBillingAddress3());
    			billingAddress.setAddressLine4(user.getBillingAddress4());
    			billingAddress.setCity(user.getBillingCity());
    			billingAddress.setState(user.getBillingState());
    			billingAddress.setZip(user.getBillingZip());
    			userType.setBillingAddress(billingAddress);
    			
    			AddressType shippingAddress = getObjectFactory().createAddressType();
    			shippingAddress.setAddressLine1(user.getShippingAddress1());
    			shippingAddress.setAddressLine2(user.getShippingAddress2());
    			shippingAddress.setAddressLine3(user.getShippingAddress3());
    			shippingAddress.setAddressLine4(user.getShippingAddress4());
    			shippingAddress.setCity(user.getShippingCity());
    			shippingAddress.setState(user.getShippingState());
    			shippingAddress.setZip(user.getShippingZip());
    			userType.setShippingAddress(shippingAddress);    	
    		}
    		
    		return userType;
     	}
    	
    	return null;
    }
    
    public static ShareTransactionType mapShareTransToShareTransType(ShareTransaction shareTransaction) {
    	
    	if(shareTransaction != null) {
    		ShareTransactionType shareType = getObjectFactory().createShareTransactionType();
    		
    		shareType.setId(shareTransaction.getId());
    		shareType.setPointsPer(shareTransaction.getPointsPer());
    		shareType.setPricePer(shareTransaction.getPricePer().doubleValue());
    		shareType.setQuantity(shareTransaction.getQuantity());
    		shareType.setStatus(shareTransaction.getStatus());
    		    		
    		return shareType;
     	}
    	
    	return null;
    }
    
    public static Users mapUserTypeToUser(UserType userType, Users user) {
    	if(userType != null) {
    		if(userType.getEmail() != null) {
    			user.setEmail(userType.getEmail());
    		}
    		if(userType.getFirstName() != null) {
    			user.setFirstName(userType.getFirstName());
    		}
    		if(userType.getLastName() != null) {
    			user.setLastName(userType.getLastName());
    		}
    		
    		if(userType.getBillingAddress() != null) {
    			user.setBillingAddress1(userType.getBillingAddress().getAddressLine1());
    			user.setBillingAddress2(userType.getBillingAddress().getAddressLine2());
    			user.setBillingAddress3(userType.getBillingAddress().getAddressLine3());
    			user.setBillingAddress4(userType.getBillingAddress().getAddressLine4());
    			user.setBillingCity(userType.getBillingAddress().getCity());
    			user.setBillingState(userType.getBillingAddress().getState());
    			user.setBillingZip(userType.getBillingAddress().getZip());    			
    		}
    		
    		if(userType.getShippingAddress() != null) {
    			user.setShippingAddress1(userType.getShippingAddress().getAddressLine1());
    			user.setShippingAddress2(userType.getShippingAddress().getAddressLine2());
    			user.setShippingAddress3(userType.getShippingAddress().getAddressLine3());
    			user.setShippingAddress4(userType.getShippingAddress().getAddressLine4());
    			user.setShippingCity(userType.getShippingAddress().getCity());
    			user.setShippingState(userType.getShippingAddress().getState());
    			user.setShippingZip(userType.getShippingAddress().getZip());
    		}   
    		
    		return user;
    	}
    	
    	return null;
    }
    public static ShareTransaction mapTransactionTypeToTransaction(ShareTransactionType transactionType, Users user, GameSession gameSession) {
    	if(transactionType != null) {
    		ShareTransaction transaction = new ShareTransaction();
   			transaction.setPointsPer(transactionType.getPointsPer());
    		transaction.setPricePer(new BigDecimal(transactionType.getPricePer()));
    		transaction.setQuantity(transactionType.getQuantity());
    		transaction.setStatus(transactionType.getStatus());
    		transaction.setUser(user);
    		return transaction;
    	}
    	return null;
    }
    
    public static AnswerType mapAnswerToAnswerType(Answer answer) {
    	AnswerType aType = getObjectFactory().createAnswerType();
    	if(answer != null) {
    		aType.setAnswer(answer.getAnswer());
    		aType.setAnswerType(answer.getAnswerType());
    		aType.setConfidence(answer.getConfidence());
    		aType.setScore(answer.getScore());   		
    		return aType;
    	} else {
    		return null;
    	}
    }
}
