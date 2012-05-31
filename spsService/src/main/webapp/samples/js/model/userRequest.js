function inituserRequest() {
	var userobj = {
		"bindings" : [ {
			"userRequest" : {
				"action" : "",
				"user" : {
					"email" : "",
					"password" : "",
					"firstName" : "",
					"lastName" : "",
					"billingAddress" : {
						"addressLine1" : "",
						"addressLine2" : "",
						"addressLine3" : "",
						"addressLine4" : "",
						"city" : "",
						"state" : "",
						"zip" : "",
						"phone" : ""
					},
					"shippingAddress" : {
						"addressLine1" : "",
						"addressLine2" : "",
						"addressLine3" : "",
						"addressLine4" : "",
						"city" : "",
						"state" : "",
						"zip" : "",
						"phone" : ""
					}
				}
			}
		} ]
	};
	
	return userobj;
	
}