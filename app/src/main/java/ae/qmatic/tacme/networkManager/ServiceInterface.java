package ae.qmatic.tacme.networkManager;

import java.util.List;

import ae.qmatic.tacme.model.AppointmentResponseModel;
import ae.qmatic.tacme.model.ConfirmAppointmentResponseModel;
import ae.qmatic.tacme.model.ConfirmedAppointmentRequestModel;
import ae.qmatic.tacme.model.DeleteAppointment;
import ae.qmatic.tacme.model.DeleteTicket;
import ae.qmatic.tacme.model.ForgotPasswordModel;
import ae.qmatic.tacme.model.GetAllBranches;
import ae.qmatic.tacme.model.GetAllBranchesOnMap;
import ae.qmatic.tacme.model.GetAvailableDates;
import ae.qmatic.tacme.model.GetAvailableTimes;
import ae.qmatic.tacme.model.RegisterRequestModel;
import ae.qmatic.tacme.model.RegisterResponseModel;
import ae.qmatic.tacme.model.RemoteBookingBranchByIDModel;
import ae.qmatic.tacme.model.RemoteBookingBranchesModel;
import ae.qmatic.tacme.model.RemoteBookingIssueTicketModel;
import ae.qmatic.tacme.model.RemoteBookingServicesModel;
import ae.qmatic.tacme.model.ServiceListModel;
import ae.qmatic.tacme.model.UserLoginModel;
import retrofit2.Call;
import retrofit2.http.Body;
import retrofit2.http.DELETE;
import retrofit2.http.GET;
import retrofit2.http.Header;
import retrofit2.http.POST;
import retrofit2.http.Path;
import retrofit2.http.Query;


/**
 * Created by mdev3 on 8/16/16.
 */
public interface ServiceInterface {

    /// USER REGISTRATION
    @POST("TAC/rest/customer")
    Call<RegisterResponseModel> userRegistration(@Header("Authorization") String auth, @Body RegisterRequestModel registerRequestModel);

    /// USER LOGIN
    @GET("TAC/rest/login?")
    Call<UserLoginModel> userLogin(@Header("Authorization") String auth, @Query("email") String emailId, @Query("password") String password);

    /// FORGOT PASSWORD
    @GET("TAC/rest/forgetPassword")
    Call<ForgotPasswordModel> forgotUserPassword(@Header("Authorization") String auth, @Query("emailId") String emailaddress);

    /// GET BRANCHES on MAP & LISTING
    @GET("TAC/rest/mobile/branches")
    Call<List<GetAllBranchesOnMap>> getAllBranchesMapAndListing (@Header("Authorization") String auth);


//    @POST("rest/appointment/appointments")
//=================Appointment-==========================

    @GET("TAC/rest/appointment/services/{serviceId}/branches")
    Call<List<GetAllBranches>> getAllBranchesBasedonService(@Path("serviceId") String serviceId);

    @GET("TAC/rest/appointment/services")
    Call<List<ServiceListModel>> getAllServices();

    @GET("TAC/rest/appointment/branches/{branchId}/services/{serviceId}/dates")
    Call<GetAvailableDates> getAvilableDates(@Path("branchId") String branchId, @Path("serviceId") String serviceId);

    @GET("TAC/rest/appointment/branches/{branchId}/services/{serviceId}/dates/{selectedDate}/times")
    Call<GetAvailableTimes> getAvilableTimes(@Path("branchId") String branchId, @Path("serviceId") String serviceId, @Path("selectedDate") String selectedDate);


    @GET("TAC/rest/appointment/branches/{branchId}/services/{serviceId}/dates/{selecteddate}/times/{selectedtime}/reserve")
    Call<AppointmentResponseModel> addAppointment(@Path("branchId") String branchId, @Path("serviceId") String serviceId, @Path("selecteddate") String selecteddate, @Path("selectedtime") String selectedtime);

    @POST("TAC/rest/appointment/{appointmentId}/confirm")
    Call<ConfirmAppointmentResponseModel> confirmAppointment(@Path("appointmentId") String appointmentId, @Body ConfirmedAppointmentRequestModel mConfirmedAppointmentRequestModel);


    @DELETE("TAC/rest/appointment/{appointmentId}")
    Call<DeleteAppointment> deleteapointment(@Path("appointmentId") String appointmentId);


    //=======================From Location===============

    @GET("TAC/rest/appointment/branches/{branchId}/services")
    Call<List<GetAllBranches>> getAllServicesBasedonBranches(@Path("branchId") String branchId);
//

    //------------------------RemoteBooking getServices-----------------------------------

    @GET("TAC/rest/mobile/services")
    Call<List<RemoteBookingServicesModel>> getAllRemoteBookingServices();

    ///RemoteBooking getBranchesBasedonServiceId
    @GET("TAC/rest/mobile/service/{serviceId}/branch")
    Call<List<RemoteBookingBranchesModel>> getAllRemoteBookingBranchesBasedonServiceId(@Path("serviceId") String serviceId, @Query("longitude") String longitude, @Query("latitude") String latitude, @Query("radius") String radius);

    // Remote Booking AvailableTime
    @GET("TAC/rest/mobile/service/{serviceId}/branch/{branchId}/issueTicket")
    Call<RemoteBookingIssueTicketModel> remoteBookingIssueTicket(@Path("serviceId") String serviceId, @Path("branchId") String branchId, @Query("delay") String delay);

    //// GET BRANCH DETAIL by BranchID
    @GET("TAC/rest/mobile/branches/{branchId}")
    Call<RemoteBookingBranchByIDModel> getAllBranchDetailsById(@Path("branchId") String branchId);

    @GET("TAC/rest/mobile/service/{serviceId}/branch/{branchId}/disposeTicket/{visitId}")
    Call<DeleteTicket> deleteTicket(@Path("serviceId") String serviceId, @Path("branchId") String branchId, @Path("visitId") String visitId);


}
