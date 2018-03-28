package soap.jaxws;

import soap.entity.Group;

import javax.jws.WebMethod;
import javax.jws.WebService;
import javax.jws.soap.SOAPBinding;

/**
 * Created by EGBoldyr on 28.03.18.
 */

@WebService
@SOAPBinding(style = SOAPBinding.Style.RPC)
public interface GroupWebService {

    @WebMethod
    boolean newGroup(String courseId, String title, String beginDate, String endDate);

    @WebMethod
    Group[] byActualDate(String actualDate);

    @WebMethod
    Group[] allGroups();
}
