package org.roncoo.pay.jackdking.middleplatformservices.tasks;

import java.util.ArrayList;
import java.util.List;
import java.util.concurrent.ArrayBlockingQueue;
import java.util.concurrent.BlockingQueue;

import org.roncoo.pay.jackdking.middleplatformservices.consts.RedisConstant;
import org.roncoo.pay.jackdking.middleplatformservices.modules.wechat.domain.MdUnlockArticle;
import org.roncoo.pay.jackdking.middleplatformservices.modules.wechat.service.IMdUnlockArticleService;
import org.roncoo.pay.jackdking.middleplatformservices.modules.wechat.service.IWeixinService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.data.redis.core.RedisTemplate;
import org.springframework.stereotype.Component;

import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;


@Slf4j
@Component
public class CollectUnlockArticleTask {
	

	@Autowired
	@Qualifier("rediTemplateA")
	private RedisTemplate redistemplate;

	@Autowired
	private  IMdUnlockArticleService iMdUnlockArticleService;
	

	@Autowired
	private  IWeixinService iWeixinService;
	
    private BlockingQueue<String> queue = new ArrayBlockingQueue<>(1024);
    
    //添加 吸引读者的文章 ，读者因为这个文章而关注公众号解锁网站
    public void addLookRecordToQueue(String articleLock) {
        if (null == articleLock) {
            return;
        }
        queue.offer(articleLock);
    }
    
    public void save() {
        List<String> bufferList = new ArrayList<>();
        while (true) {
            try {
                bufferList.add(queue.take());//articleLook=  appid:articleid
                for (String articleInfo : bufferList) {
                    MdUnlockArticle mdUnlockArticle = new MdUnlockArticle();
                    mdUnlockArticle.setAppid(Long.valueOf(articleInfo.split(":")[0]));
                    mdUnlockArticle.setArticleId(Long.valueOf(articleInfo.split(":")[1]));
                    //如果  token的key被删除了，则不计算文章吸引的关注量。
                    
                    if(!redistemplate.delete(RedisConstant.USER_SUBS_TOKEN_PREFIX+articleInfo.split(":")[2]))
                    {	
                    	log.info("该用户token近期未 在公众号  发送该消息:{}", articleInfo);
                    	continue;
                    }
                    
                    
                	log.info("该用户token近期在公众号  发送该消息 :{}", articleInfo);
                    
                    if (!iMdUnlockArticleService.isExist(mdUnlockArticle.getArticleId(),mdUnlockArticle.getAppid())) {
                        log.warn("{}-该文章不存在！，插入新文章", articleInfo);
                        mdUnlockArticle.setUnlockCount(1);
                        iMdUnlockArticleService.insertMdUnlockArticle(mdUnlockArticle);
                        continue;
                    }
                    //更新解锁次数
                    iMdUnlockArticleService.updateMdUnlockArticle(mdUnlockArticle);
                }
                
            } catch (InterruptedException e) {
                log.error("保存文章浏览记录失败--->[{}]", e.getMessage());
                // 防止缓冲队列填充数据出现异常时不断刷屏
                try {
                    Thread.sleep(1000);
                } catch (Exception err) {
                    log.error(err.getMessage());
                }
            } finally {
                bufferList.clear();
            }
        }
    }
    
    
}
