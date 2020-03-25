package lan.qxc.lightclient.service;

import java.io.File;
import java.util.ArrayList;
import java.util.List;

import lan.qxc.lightclient.entity.Dongtai;
import lan.qxc.lightclient.result.Result;
import lan.qxc.lightclient.retrofit_util.RetrofitHelper;
import lan.qxc.lightclient.retrofit_util.service.DongtaiRequestService;
import lan.qxc.lightclient.retrofit_util.service.UserRequestService;
import okhttp3.MediaType;
import okhttp3.MultipartBody;
import okhttp3.RequestBody;
import retrofit2.Call;

public class DongtaiServicce {

    private static class  DongtaiServicceHolder{
        static DongtaiServicce instance = new DongtaiServicce();
    }

    public static DongtaiServicce getInstance(){
        return DongtaiServicceHolder.instance;
    }

//    public Call<Result> addDongtai(Dongtai dongtai){
//        DongtaiRequestService service = RetrofitHelper.getInstance().create(DongtaiRequestService.class);
//        return service.addDongtai(dongtai);
//    }

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

    public Call<Result> addDongtai(List<File> files, String dttext, Long uesrid){
        DongtaiRequestService service = RetrofitHelper.getInstance().create(DongtaiRequestService.class);

        List<MultipartBody.Part> parts = new ArrayList<>();

        for(File file : files){
            RequestBody requestFile = RequestBody.create(MediaType.parse("multipart/form-data"), file);
            MultipartBody.Part part = MultipartBody.Part.createFormData("pic", file.getName(), requestFile);
            parts.add(part);
        }
        if(parts.size()==0){
            return service.addDongtaiNotPic(dttext,uesrid);
        }

        return service.addDongtai(parts,dttext,uesrid);
    }

}
