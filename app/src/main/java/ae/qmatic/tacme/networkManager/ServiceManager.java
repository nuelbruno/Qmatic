package ae.qmatic.tacme.networkManager;

/**
 * Created by mdev3 on 8/16/16.
 */
public class ServiceManager {

  public  ServiceInterface apiService;
    public ServiceManager(){

        apiService   =
                ApiClient.getClient().create(ServiceInterface.class);
    }

}
