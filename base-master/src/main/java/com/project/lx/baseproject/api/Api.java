package com.project.lx.baseproject.api;

import com.project.lx.baseproject.base.BaseEntity;
import com.project.lx.baseproject.bean.request.AttentionBody;
import com.project.lx.baseproject.bean.request.AttentionOrcancelAttentionBody;
import com.project.lx.baseproject.bean.request.ChangePwForOldPwdBody;
import com.project.lx.baseproject.bean.request.EditNickNameBody;
import com.project.lx.baseproject.bean.request.FeedBackBody;
import com.project.lx.baseproject.bean.request.FinishDemandBody;
import com.project.lx.baseproject.bean.request.GetCodeBody;
import com.project.lx.baseproject.bean.request.GetScanMessageBody;
import com.project.lx.baseproject.bean.request.LoginBody;
import com.project.lx.baseproject.bean.request.LoginByOtherBody;
import com.project.lx.baseproject.bean.request.MessageBody;
import com.project.lx.baseproject.bean.request.ReleaseBody;
import com.project.lx.baseproject.bean.request.ReleaseDemandBody;
import com.project.lx.baseproject.bean.request.ReportOthersBody;
import com.project.lx.baseproject.bean.request.SearchBody;
import com.project.lx.baseproject.bean.request.SearchResultBody;
import com.project.lx.baseproject.bean.request.ShopBody;
import com.project.lx.baseproject.bean.request.ShopOnMapBody;
import com.project.lx.baseproject.bean.request.ShopsLetterBody;
import com.project.lx.baseproject.bean.request.ShopsLocatedBody;
import com.project.lx.baseproject.bean.request.ShowLetterBody;
import com.project.lx.baseproject.bean.request.ShowMyLetterBody;
import com.project.lx.baseproject.bean.request.ShowShopInfoBody;
import com.project.lx.baseproject.bean.request.ShowUserInfoBody;
import com.project.lx.baseproject.bean.request.ShowUserInformationBody;
import com.project.lx.baseproject.bean.request.SingInBody;
import com.project.lx.baseproject.bean.request.SubLetterBody;
import com.project.lx.baseproject.bean.request.SysUpdateSwitchBody;
import com.project.lx.baseproject.bean.request.TimeBody;
import com.project.lx.baseproject.bean.request.VerfyReceiveBody;
import com.project.lx.baseproject.bean.request.VerifyPhoneBody;
import com.project.lx.baseproject.bean.responses.AreaInfo;
import com.project.lx.baseproject.bean.responses.AttentionContentInfo;
import com.project.lx.baseproject.bean.responses.AttentionInfo;
import com.project.lx.baseproject.bean.responses.ChangePwdForCodeInfo;
import com.project.lx.baseproject.bean.responses.ContactsInfo;
import com.project.lx.baseproject.bean.responses.DemandInfo;
import com.project.lx.baseproject.bean.responses.EmptyInfo;
import com.project.lx.baseproject.bean.responses.GetScanMessInfo;
import com.project.lx.baseproject.bean.responses.LetterInfo;
import com.project.lx.baseproject.bean.responses.MessageInfo;
import com.project.lx.baseproject.bean.responses.ProtocolInfo;
import com.project.lx.baseproject.bean.responses.SearchHotInfo;
import com.project.lx.baseproject.bean.responses.SearchResultInfo;
import com.project.lx.baseproject.bean.responses.ShopInfo;
import com.project.lx.baseproject.bean.responses.ShopLetterContentsInfo;
import com.project.lx.baseproject.bean.responses.ShopLetterInfo;
import com.project.lx.baseproject.bean.responses.ShopOnMapInfo;
import com.project.lx.baseproject.bean.responses.ShowShopInfo;
import com.project.lx.baseproject.bean.responses.ShowUserInformationInfo;
import com.project.lx.baseproject.bean.responses.SwitchStateInfo;
import com.project.lx.baseproject.bean.responses.TimeInfo;
import com.project.lx.baseproject.bean.responses.TopicInfo;
import com.project.lx.baseproject.bean.responses.UserInfo;

import java.util.List;

import io.reactivex.Observable;
import okhttp3.MultipartBody;
import retrofit2.http.Body;
import retrofit2.http.Multipart;
import retrofit2.http.POST;
import retrofit2.http.Part;

/**
 * @author: X_Meteor
 * @description: 类描述
 * @version: V_1.0.0
 * @date: 2017/4/7 0007 13:56
 * @company:
 * @email: lx802315@163.com
 */
public interface Api {

    @POST("api/user/loginByPassword")
    Observable<BaseEntity<UserInfo>> login(@Body LoginBody body);

    @POST("api/user/sendSMS")
    Observable<BaseEntity> getCode(@Body GetCodeBody body);

    @POST("api/user/registByPhone")
    Observable<BaseEntity> singIn(@Body SingInBody body);

    @POST("api/user/findPwdByPhone")
    Observable<BaseEntity<ChangePwdForCodeInfo>> changePwdForCode(@Body SingInBody body);

    @POST("api/user/showUserInfo")
    Observable<BaseEntity<UserInfo>> showUserInfo(@Body ShowUserInfoBody body);

    @POST("api/user/changePwd")
    Observable<BaseEntity> changePwd(@Body ChangePwForOldPwdBody body);

    @POST("api/user/editNickName")
    Observable<BaseEntity> editNickName(@Body EditNickNameBody body);

    @POST("api/user/editUserImg")
    Observable<BaseEntity> editUserImg(@Body EditNickNameBody body);

    @POST("api/letter/searchTopic")
    Observable<BaseEntity<TopicInfo>> searchTopic(@Body SearchBody body);

    @POST("api/letter/selectTopic")
    Observable<BaseEntity<TopicInfo>> selectTopic(@Body SearchBody body);

    @POST("api/letter/selectArea")
    Observable<BaseEntity<List<AreaInfo>>> getArea();

    @POST("api/letter/selectShop")
    Observable<BaseEntity<List<ShopInfo>>> getShop(@Body ShopBody body);

    @POST("api/letter/selectFriend")
    Observable<BaseEntity<ContactsInfo>> chooseRecipicent(@Body SearchBody body);

    @POST("api/letter/sendLetter")
    Observable<BaseEntity> releaseLetter(@Body ReleaseBody body);

    @POST("api/letter/sendDemand")
    Observable<BaseEntity> releaseDemand(@Body ReleaseDemandBody body);

    @POST("api/user/showMyAttentionFans")
    Observable<BaseEntity<AttentionInfo>> showMyAttentionFans(@Body AttentionBody body);

    @POST("api/user/showMyAttentionFansCount")
    Observable<BaseEntity<AttentionContentInfo>> showMyAttentionFansCount(@Body SearchBody body);

    @POST("api/shop/shopOnMap")
    Observable<BaseEntity<List<ShopOnMapInfo>>> shopOnMap(@Body ShopOnMapBody body);

    @POST("api/shop/showShopInfo")
    Observable<BaseEntity<ShowShopInfo>> showShopInfo(@Body ShowShopInfoBody body);

    @POST("api/letter/subscribeLetter")
    Observable<BaseEntity> subscribeLetter(@Body SubLetterBody body);

    @POST("api/letter/showMyLetter")
    Observable<BaseEntity<LetterInfo>> showMyLetter(@Body ShowMyLetterBody body);

    @POST("api/letter/showMyDemand")
    Observable<BaseEntity<DemandInfo>> showMyDemand(@Body ShowMyLetterBody body);

    @POST("api/letter/finishDemand")
    Observable<BaseEntity> finishDemand(@Body FinishDemandBody body);

    @POST("api/shop/showCountdown")
    Observable<BaseEntity<TimeInfo>> showCountdown(@Body TimeBody body);

    @POST("api/user/showUserInformation")
    Observable<BaseEntity<ShowUserInformationInfo>> showUserInformation(@Body ShowUserInformationBody body);

    @POST("api/user/attentionOrcancelAttention")
    Observable<BaseEntity> attentionOrcancelAttention(@Body AttentionOrcancelAttentionBody body);

    @POST("api/mSystem/reportOthers")
    Observable<BaseEntity> reportOthers(@Body ReportOthersBody body);

    @POST("api/mSystem/feedback")
    Observable<BaseEntity> feedback(@Body FeedBackBody body);

    @POST("api/shop/shopsLocated")
    Observable<BaseEntity> shopsLocated(@Body ShopsLocatedBody body);

    @POST("api/shop/shopsLetterCount")
    Observable<BaseEntity<ShopLetterContentsInfo>> shopsLetterCount(@Body SearchBody body);

    @POST("api/shop/shopsLetter")
    Observable<BaseEntity<ShopLetterInfo>> shopsLetter(@Body ShopsLetterBody body);

    @POST("api/mSystem/sySearchPage")
    Observable<BaseEntity<SearchHotInfo>> sySearchPage(@Body SearchBody body);

    @POST("api/mSystem/sySearch")
    Observable<BaseEntity<SearchResultInfo>> sySearch(@Body SearchResultBody body);

    @POST("api/user/loginByOther")
    Observable<BaseEntity<UserInfo>> loginByOther(@Body LoginByOtherBody body);

    @POST("api/mSystem/getSwitchState")
    Observable<BaseEntity<SwitchStateInfo>> getSwitchState(@Body SearchBody body);

    @POST("api/mSystem/sysUpdateSwitch")
    Observable<BaseEntity> sysUpdateSwitch(@Body SysUpdateSwitchBody body);

    @POST("api/shop/verifyReceive")
    Observable<BaseEntity> verifyReceive(@Body VerfyReceiveBody body);

    @POST("api/mSystem/messList")
    Observable<BaseEntity<List<MessageInfo>>> getMessage(@Body MessageBody body);

    @POST("api/mSystem/protocol")
    Observable<BaseEntity<ProtocolInfo>> protocol();

    @POST("api/shop/getMessage")
    Observable<BaseEntity<GetScanMessInfo>> getScanMessage(@Body GetScanMessageBody body) ;

    @POST("api/user/verifyPhone")
    Observable<BaseEntity> verifyPhone(@Body VerifyPhoneBody body) ;

    @POST("upload/api/uploadPic.do ")
    @Multipart
    Observable<BaseEntity> uploadFile(@Part MultipartBody.Part file);
}
