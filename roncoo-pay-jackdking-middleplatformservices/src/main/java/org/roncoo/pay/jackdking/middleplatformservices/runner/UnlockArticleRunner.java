package org.roncoo.pay.jackdking.middleplatformservices.runner;

import org.roncoo.pay.jackdking.middleplatformservices.tasks.CollectUnlockArticleTask;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.ApplicationArguments;
import org.springframework.boot.ApplicationRunner;
import org.springframework.stereotype.Component;

@Component
public class UnlockArticleRunner  implements ApplicationRunner{
	
	@Autowired
	CollectUnlockArticleTask collectUnlockArticleTask;

	@Override
	public void run(ApplicationArguments args) throws Exception {
		// TODO Auto-generated method stub
		collectUnlockArticleTask.save();
	}

}
