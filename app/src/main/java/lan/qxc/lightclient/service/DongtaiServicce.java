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

    public Call<Result> getDongtai_Back_List(Long userid,Long dtid){
        DongtaiRequestService service = RetrofitHelper.getInstance().create(DongtaiRequestService.class);
        return service.getDongtai_Back_List(userid,dtid);
    }

    public Call<Result> getDongtai_New_List(Long userid){
        DongtaiRequestService service = RetrofitHelper.getInstance().create(DongtaiRequestService.class);
        return service.getDongtai_New_List(userid);
    }

    public Call<Result> getUserDongtai_Back_List(Long uid,Long userid,Long dtid){
        DongtaiRequestService service = RetrofitHelper.getInstance().create(DongtaiRequestService.class);
        return service.getUserDongtai_Back_List(uid,userid,dtid);
    }

    public Call<Result> getUserDongtai_New_List(Long uid,Long userid){
        DongtaiRequestService service = RetrofitHelper.getInstance().create(DongtaiRequestService.class);
        return service.getUserDongtai_New_List(uid,userid);
    }


    public Call<Result> addDongtai(List<File> files, String dttext, String deviceinfo,Long uesrid){
        DongtaiRequestService service = RetrofitHelper.getInstance().create(DongtaiRequestService.class);

        List<MultipartBody.Part> parts = new ArrayList<>();

        for(File file : files){
            RequestBody requestFile = RequestBody.create(MediaType.parse("multipart/form-data"), file);
            MultipartBody.Part part = MultipartBody.Part.createFormData("pic", file.getName(), requestFile);
            parts.add(part);
        }
        if(parts.size()==0){
            return service.addDongtaiNotPic(dttext,deviceinfo,uesrid);
        }

        return service.addDongtai(parts,dttext,deviceinfo,uesrid);
    }


    //点赞
    public Call<Result> likeDongtai(Long dtid,Long userid){
        DongtaiRequestService service = RetrofitHelper.getInstance().create(DongtaiRequestService.class);
        return service.likeDongtai(dtid,userid);
    }


    public Call<Result> getDongtai_Msg_Not_Read_Num(Long userid){
        DongtaiRequestService service = RetrofitHelper.getInstance().create(DongtaiRequestService.class);
        return service.getDongtai_Msg_Not_Read_Num(userid);
    }


    public Call<Result> getDongtai_Msg_Back_List(Long userid,Long msgid){
        DongtaiRequestService service = RetrofitHelper.getInstance().create(DongtaiRequestService.class);
        return service.getDongtai_Msg_Back_List(userid,msgid);
    }

    public Call<Result> getDongtai_Msg_New_List(Long userid){
        DongtaiRequestService service = RetrofitHelper.getInstance().create(DongtaiRequestService.class);
        return service.getDongtai_Msg_New_List(userid);
    }

    public Call<Result> set_Dongtai_Msg_Had_Read(Long userid){
        DongtaiRequestService service = RetrofitHelper.getInstance().create(DongtaiRequestService.class);
        return service.set_Dongtai_Msg_Had_Read(userid);
    }
}
