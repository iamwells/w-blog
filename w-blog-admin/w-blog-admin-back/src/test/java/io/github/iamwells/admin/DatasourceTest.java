package io.github.iamwells.admin;

import com.hankcs.hanlp.HanLP;
import io.github.iamwells.admin.util.HanLPUtil;
import org.junit.jupiter.api.Test;

import java.io.File;

//@SpringBootTest
public class DatasourceTest {


//    @Autowired
//    private UserService userService;
//
//    @Autowired
//    private UserMapper userMapper;
//
//    @Autowired
//    private ObjectMapper objectMapper;
//
//
//    @Autowired
//    private PasswordEncoder passwordEncoder;
//
//    @Autowired
//    private MailService mailService;


    @Test
    public void test() {
        System.out.println(new File(HanLP.Config.CoreDictionaryPath).exists());
        String text = "Tom，谢谢你，今天我很高兴，我请你吃价值999元的大餐！";
        System.out.println(HanLPUtil.segmentForIndex(text));
        System.out.println(HanLPUtil.segmentForSearch(text));
        System.out.println(HanLPUtil.segment(HanLPUtil.SegmentType.SEARCH, text, "/"));
    }
}
