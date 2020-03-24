package lan.qxc.lightclient.service;

import lan.qxc.lightclient.entity.Dongtai;
import lan.qxc.lightclient.result.Result;
import lan.qxc.lightclient.retrofit_util.RetrofitHelper;
import lan.qxc.lightclient.retrofit_util.service.DongtaiRequestService;
import retrofit2.Call;

public class DongtaiServicce {

    private static class  DongtaiServicceHolder{
        static DongtaiServicce instance = new DongtaiServicce();
    }

    public static DongtaiServicce getInstance(){
        return DongtaiServicceHolder.instance;
    }

    public Call<Result> addDongtai(Dongtai dongtai){
        DongtaiRequestService service = RetrofitHelper.getInstance().create(DongtaiRequestService.class);
        return service.addDongtai(dongtai);
    }

    public Call<Result> deleteDongtai(Long dtid){
        DongtaiRequestService service = RetrofitHelper.getInstance().create(DongtaiRequestService.class);
        return service.deleteDongtai(dtid);
    }

    public Call<Result> updateDngtai(Dongtai dongtai){
        DongtaiRequestService service = RetrofitHelper.getInstance().create(DongtaiRequestService.class);
        return service.updateDongtai(dongtai);
    }

    public Call<Result> getDongtai_Back_List(Long dtid){
        DongtaiRequestService service = RetrofitHelper.getInstance().create(DongtaiRequestService.class);
        return service.getDongtai_Back_List(dtid);
    }

    public Call<Result> getDongtai_New_List(){
        DongtaiRequestService service = RetrofitHelper.getInstance().create(DongtaiRequestService.class);
        return service.getDongtai_New_List();
    }

}
