package sbgl3.yunda.service;

import android.app.Service;
import android.content.Intent;
import android.media.AudioManager;
import android.media.SoundPool;
import android.os.Handler;
import android.os.IBinder;
import android.os.Message;
import android.os.PowerManager;
import android.support.annotation.Nullable;

import com.jess.arms.utils.utilcode.util.StringUtils;
import com.jess.arms.utils.utilcode.util.ToastUtils;
import com.speedata.libuhf.IUHFService;
import com.speedata.libuhf.Tag_Data;
import com.speedata.libuhf.UHFManager;

import org.greenrobot.eventbus.EventBus;
import org.greenrobot.eventbus.Subscribe;
import org.greenrobot.eventbus.ThreadMode;

import java.util.ArrayList;
import java.util.List;

import io.reactivex.Observable;
import io.reactivex.ObservableEmitter;
import io.reactivex.ObservableOnSubscribe;
import io.reactivex.android.schedulers.AndroidSchedulers;
import io.reactivex.schedulers.Schedulers;
import com.jess.arms.base.AppConstant;
import sbgl3.yunda.entry.EpcDataBase;
import sbgl3.yunda.entry.HexStrUtil;
import sbgl3.yunda.entry.MessageBean;

/**
 * RFID扫描相关服务
 * @author 周雪巍
 * @time 2018/09/12
 * */
public class ScanService extends Service {
    // 二维码扫描Action返回码
    private static final int REQUEST_CODE_SCAN = 0x0000;

    // 二维码扫描内容key
    private static final String DECODED_CONTENT_KEY = "codedContent";

    // 二维码扫描截图（bitmap）key
    // private static final String DECODED_BITMAP_KEY = "codedBitmap";

    // 扫描到标签
    public static final int MESSAGE_SCAN_SUCC = 1;

    // 释放扫描键
    public static final int MESSAGE_SCAN_KEY_UP = 2;

    // 打开RFID扫描设备成功
    public static final int MESSAGE_OPEN_SUCC = 3;

    // 打开RFID扫描设备失败
    public static final int MESSAGE_OPEN_FAILURE = 4;

    // 关闭RFID扫描成功
    public static final int MESSAGE_CLOSE_SUCC = 0;

    // 扫描键KEY_CODE
    public static final int SCAN_KEY_CODE_134 = 134;
    public static final int SCAN_KEY_CODE_135 = 135;

    // 标签读取地址
    public static final String READ_ADDRESS = "0";

    // EPC分区保存数据的最大长度
    public static final int EPC_MAX_LEN = 10;

    // 默认密码
    public static final String DEFAULT_PW = "0";

    public static final String DEFAULT_COUNT = "10";

    // USER区最大长度
    public static final int USER_MAX_LEN = 20;

    private PowerManager pM = null;

    private PowerManager.WakeLock wK = null;

    // 用于接收扫描数据和读取标签中的数据
    private Handler handler = null;

    private SoundPool soundPool;

    private int soundId;

    // 是否已开启扫描
    private boolean inSearch = false;
    // KT50 扫描借口 hy 2017.7.24添加
    private IUHFService iuhfService;

    private List<EpcDataBase> firm = new ArrayList<EpcDataBase>();
    @Nullable
    @Override
    public IBinder onBind(Intent intent) {
        return null;
    }

    @Override
    public int onStartCommand(Intent intent, int flags, int startId) {
        if(iuhfService==null){
            try {
                iuhfService = UHFManager.getUHFService(this);
                if (iuhfService == null) {
                    ToastUtils.showShort("没有找到RFID扫描模块");
                }else {
                    ToastUtils.showShort("找到RFID扫描模块");
                }
            } catch (Exception e) {
                ToastUtils.showShort("没有找到RFID扫描模块");
            }
        }

        if(pM==null){
            // 获取电源管理对象
            pM = (PowerManager) getSystemService(POWER_SERVICE);
            if (pM != null) {
                // 获取屏幕唤醒管理对象，实现屏幕长亮
                wK = pM.newWakeLock(PowerManager.SCREEN_BRIGHT_WAKE_LOCK | PowerManager.ON_AFTER_RELEASE, "rfid_lock");
                if (wK != null) {
                    wK.acquire();
                }
            }
        }


        if(soundPool==null){
            // 初始化声音播放对象
            soundPool = new SoundPool(2, AudioManager.STREAM_MUSIC, 0);
            if (soundPool == null) {
                ToastUtils.showShort("开启扫描声音失败！");
            }
            soundId = soundPool.load("/system/media/audio/ui/VideoRecord.ogg", 0);
        }
        MessageBean messageBean = new MessageBean();
        messageBean.setMsgType("startFinish");
        EventBus.getDefault().post(messageBean);
        if(handler==null){
            // 多线程消息管理器，用于在子线程中开启、关闭RFID扫描设备和接收扫描返回数据
            handler = new Handler() {
                @SuppressWarnings("unchecked")
                @Override
                public void handleMessage(Message msg) {
                    super.handleMessage(msg);
                    // 标签扫描结果收集
                    if (msg.what == MESSAGE_SCAN_SUCC) {
                        ArrayList<Tag_Data> ks = (ArrayList<Tag_Data>) msg.obj;
                        String tmp[] = new String[ks.size()];
                        for (int i = 0; i < ks.size(); i++) {
                            byte[] nq = ks.get(i).epc;
                            if (nq != null) {
                                tmp[i] = new String();
                                for (int j = 0; j < nq.length; j++) {
                                    tmp[i] += String.format("%02x ", nq[j]);
                                }
                            }
                        }
                        int i, j;
                        for (i = 0; i < tmp.length; i++) {
                            for (j = 0; j < firm.size(); j++) {
                                if (tmp[i].equals(firm.get(j).epc)) {
                                    firm.get(j).valid++;
                                    break;
                                }
                            }
                            if (j == firm.size()) {
                                firm.add(new EpcDataBase(tmp[i], 1));
                                soundPool.play(soundId, 1, 1, 0, 0, 1);
                            }
                        }
                    } else if (msg.what == MESSAGE_SCAN_KEY_UP) {
                        callTrigger();
                    } else if (msg.what == MESSAGE_OPEN_SUCC) { // RFID扫描设备开启成功
                        iuhfService.reg_handler(handler);
                        Observable.create(new ObservableOnSubscribe<Object>() {
                            @Override
                            public void subscribe(ObservableEmitter<Object> emitter) throws Exception {
                                ToastUtils.showShort("RFID扫描设备启动成功！");
                            }
                        }).subscribeOn(AndroidSchedulers.mainThread())
                                .subscribe();
                    } else if (msg.what == MESSAGE_OPEN_FAILURE) {
                        Observable.create(new ObservableOnSubscribe<Object>() {
                            @Override
                            public void subscribe(ObservableEmitter<Object> emitter) throws Exception {
                                ToastUtils.showShort("RFID扫描设备启动失败！");
                                ScanService.this.onDestroy();
                            }
                        }).subscribeOn(AndroidSchedulers.mainThread())
                                .subscribe();
                    } else if (msg.what == MESSAGE_CLOSE_SUCC) {

                    }
                }
            };
        }
        Observable.create(new ObservableOnSubscribe<Object>() {
            @Override
            public void subscribe(ObservableEmitter<Object> emitter) throws Exception {
                if (iuhfService == null || iuhfService.OpenDev() != 0) {
                    handler.sendEmptyMessage(MESSAGE_OPEN_FAILURE);
                } else {
                    handler.sendEmptyMessage(MESSAGE_OPEN_SUCC);
                }
            }
        }).subscribeOn(Schedulers.newThread()).subscribe();
        return super.onStartCommand(intent, flags, startId);

    }
    private void callTrigger() {
        // 遍历firm，依次选择标签并读取USER分区中的设备编码
        if (firm.size() <= 0) {
            ToastUtils.showShort("未扫描到标签！");
            return;
        }

        ArrayList<EpcDataBase> list = new ArrayList<EpcDataBase>();
        for (EpcDataBase epcDataBase : firm) {
            int res = select_UHF(epcDataBase.epc);
            if (res == -1) {
                ToastUtils.showShort("RFID选卡失败，请重新扫描!!");
                return;
            }
            // 选择标签成功后，读取用户数据
            if (res == 0) {
                String codeHex = read_card(IUHFService.USER_A, READ_ADDRESS, DEFAULT_COUNT, DEFAULT_PW);
                if (!StringUtils.isTrimEmpty(codeHex)) {
                    String fixedCode = HexStrUtil.hexStr2Str(codeHex.trim()).trim();
                    int idx = fixedCode.indexOf(AppConstant.DEV_CODE_FIX_CHAR);
                    if (idx != -1) {
                        fixedCode = fixedCode.substring(0, idx);
                    }
                    EpcDataBase dataBase = new EpcDataBase();
                    dataBase.equipmentCode = fixedCode;
                    list.add(dataBase);
                }
            }
        }
        EventBus.getDefault().post(list);
    }

    /**
     * <li>说明：选择标签
     * <li>创建人：黄杨
     * <li>创建日期：2017年7月24日
     * <li>修改人：
     * <li>修改日期：
     * <li>修改内容：
     *
     * @param epc 要操作的epc标签
     * @return
     */
    public int select_UHF(String epc) {
        if (iuhfService.select_card(epc) != 0) {
            return -1;
        }
        return 0;
    }

    /**
     * <li>说明：向RFID标签的EPC区写入数据
     * <li>创建人：黄杨
     * <li>创建日期：2017年7月24日
     * <li>修改人：
     * <li>修改日期：
     * <li>修改内容：
     *
     * @param epclength
     * @param passwd
     * @param EPC
     * @return 成功返回0，失败返回其它
     */
    public int set_EPC(int epclength, String passwd, byte[] EPC) {
        byte[] res;
        long pss = 0;
        if (epclength > 31) {
            return -3;
        }
        if (epclength * 2 < EPC.length) {
            return -3;
        }
        try {
            pss = Long.parseLong(passwd, 16);
        } catch (NumberFormatException e) {
            return -4;
        }
        res = iuhfService.read_area(1, 1, 2, 0);
        if (res == null) {
            return -5;
        }
        res[0] = (byte) ((res[0] & 0x7) | (epclength << 3));
        byte[] f = new byte[2 + epclength * 2];
        try {
            System.arraycopy(res, 0, f, 0, 2);
            System.arraycopy(EPC, 0, f, 2, epclength * 2);
        } catch (Exception e) {
            e.printStackTrace();
            return -1;
        }
        return iuhfService.write_area(1, 1, (int) pss, f);
    }

    /**
     * <li>说明：读取标签内用户数据
     * <li>创建人：黄杨
     * <li>创建日期：2017年7月25日
     * <li>修改人：
     * <li>修改日期：
     * <li>修改内容：
     *
     * @param area   功能区
     * @param addr   要读地址
     * @param count  读取块数
     * @param passwd 访问口令
     * @return 成功返回String，内容为读取到的数据，失败返回null
     */
    public String read_card(int area, String addr, String count, String passwd) {
        // 此方法已包含数据转换
        String v = iuhfService.read_area(area, addr, count, passwd);
        if (v == null) {
            return null;
        }
        return v;
    }

    /**
     * <li>说明：将用户数据写入标签
     * <li>创建人：黄杨
     * <li>创建日期：2017年7月25日
     * <li>修改人：
     * <li>修改日期：
     * <li>修改内容：
     *
     * @param area    要写的区域
     * @param addr    要读其实地址
     * @param pwd     访问口令
     * @param count   写入的块数
     * @param content 要写入的数据
     * @return 成功返回0；写入失败返回-1；内容长度有误返回-2；无效字符返回-3；
     */
    public int write_card(int area, String addr, String pwd, String count, String content) {
        return iuhfService.write_area(area, addr, pwd, count, content);
    }
    @Subscribe(threadMode = ThreadMode.MAIN)
    public void getKeyDown(String event){
        if(event.equals("onKeyDown")){
            firm.clear();
            iuhfService.inventory_start();
            inSearch = true;
            ToastUtils.showShort("开始扫描");
        }else if(event.equals("onKeyUp")){
            // 关闭扫描
            iuhfService.inventory_stop();
            inSearch = false;
            handler.sendEmptyMessage(MESSAGE_SCAN_KEY_UP);
        }
    }
    @Subscribe(threadMode = ThreadMode.MAIN)
    public void getKeyDown(MessageBean event){
        if(event!=null){
            if("regcard".equals(event.getMsgType())){
                String code = event.getMsgInfo();
                // 处理设备编码（补足10个字符），用于保存到RFID标签内
                String fixedCode = "";
                if (code.length() < 20) {
                    for (int i = 1; i <= 20 - code.length(); i++) {
                        fixedCode += AppConstant.DEV_CODE_FIX_CHAR;
                    }
                }
                // 获取EPC，EPC = 设备编码（等于10字符）|截取后10个字符（大于10字符）|补充特殊字符（小于10字符）
                String epc = "";
                if (code.length() ==10) {
                    epc = code;
                } else if (code.length() < 10) {
                    epc = code;
                    for (int i = 1; i <= 10 - code.length(); i++) {
                        epc += AppConstant.DEV_CODE_FIX_CHAR;
                    }
                } else if (code.length() > 10) {
                    epc = code.substring(code.length() -10);
                }
                // 重写EPC区
                if (set_EPC(5, "0", epc.getBytes()) != 0) {
                    ToastUtils.showShort("EPC区操作失败，请靠近标签保持静止重新登记设备！");
                    return;
                }

                // 重新选卡
                if (select_UHF(HexStrUtil.hex2SpaceHex(HexStrUtil.str2HexStr(epc))) != 0) {
                    ToastUtils.showShort("EPC区操作失败，请靠近标签保持静止重新登记设备！");
                    return;
                }

                // 将设备编码写入User区
                if (write_card(IUHFService.USER_A, "0", "0", "10", HexStrUtil.hex2SpaceHex(HexStrUtil.str2HexStr(code + fixedCode))) != 0) {
                    ToastUtils.showShort("用户区写入失败，请靠近标签保持静止重新登记设备！");
                    return;
                }
                MessageBean messageBean = new MessageBean();
                messageBean.setSuccess(true);
                messageBean.setMsgInfo(code);
                messageBean.setMsgType("regSuccess");
                EventBus.getDefault().post(messageBean);
            }
        }
    }
    @Override
    public void onCreate() {
        super.onCreate();
        if(!EventBus.getDefault().isRegistered(ScanService.this)){
            EventBus.getDefault().register(ScanService.this);
        }
    }

    @Override
    public void onDestroy() {
        super.onDestroy();
        if(EventBus.getDefault().isRegistered(ScanService.this)){
            EventBus.getDefault().unregister(ScanService.this);
        }
        ToastUtils.showShort("关闭RFID服务");
    }
}
