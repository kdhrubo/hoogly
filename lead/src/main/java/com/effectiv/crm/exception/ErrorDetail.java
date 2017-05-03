package com.effectiv.crm.exception;

import java.io.Serializable;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import com.fasterxml.jackson.annotation.JsonInclude;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

/**
 * @see <a href=
 *      "http://tools.ietf.org/html/draft-nottingham-http-problem-06">draft-nottingham-http-problem-06</a>
 */

@Data
@AllArgsConstructor
@NoArgsConstructor
@JsonInclude(JsonInclude.Include.NON_NULL)
public class ErrorDetail implements Serializable {

	private static final long serialVersionUID = 1L;
	/**
	 * The title field provides a brief title for the error condition. 
	 * For example, errors resulting as a result of input validation 
	 * will have the title �Validation Failure�. 
	 * Similarly, an �Internal Server Error� will be used for internal server errors
	 */

	private String title;

	/**
	 * The HTTP status code generated by the origin server for this occurrence
	 * of the problem.
	 */
	private int status;

	/**
	 * An human readable explanation specific to this occurrence of the problem.
	 */

	private String detail;
	/**
     * The time in milliseconds when the error happened on the server.
     */
	
	private long timeStamp;
	
	
	
	/**
	 * The developerMessage contains information such as 
	 * exception class name that is relevant to developers.
	 * It can also add an URI where developers can find more details on this type
	 * of error
	 */
	private String developerMessage;
	
	/**
	 * The errors field is used to report field validation errors.
	 */
	
    private Map<String, List<ValidationError>> errors = new HashMap<String, List<ValidationError>>();
    
    private String path;

}

@Data
class ValidationError {

    private String code;
    private String message;

    
}
