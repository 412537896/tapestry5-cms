package org.flywind.cms.pages;

import java.util.Date;
import java.util.List;

import org.apache.tapestry5.annotations.InjectComponent;
import org.apache.tapestry5.annotations.InjectPage;
import org.apache.tapestry5.annotations.OnEvent;
import org.apache.tapestry5.annotations.Persist;
import org.apache.tapestry5.annotations.Property;
import org.apache.tapestry5.corelib.components.Zone;
import org.apache.tapestry5.ioc.annotations.Inject;
import org.apache.tapestry5.services.ajax.JavaScriptCallback;
import org.apache.tapestry5.services.javascript.JavaScriptSupport;
import org.flywind.business.entities.cms.Posts;
import org.flywind.business.services.cms.PostsService;
import org.flywind.cms.base.AppBase;
import org.flywind.widgets.WidgetSymbolConstants;
import org.flywind.widgets.core.dao.FPage;

public class Start extends AppBase {
	
	@Property
	private Long tid;

	@Property
	private Posts posts;
	
	@Property
	private List<Posts> postss;
	
	@Inject
	private PostsService postsService;
	
	@InjectComponent
	private Zone dataZone;
	
	@InjectPage
	private Tags tagPage;
	
	public Object onGo(Long id){
		tagPage.setParams(id);
		return tagPage;
	}
	
	@OnEvent(component="plist",value=WidgetSymbolConstants.PAGER_LOAD_DATA)
	public void getPostsData(){
		
		if(posts == null){
			posts = new Posts();
			posts.setIsHome(Boolean.TRUE);
		}
		FPage page = (FPage)request.getAttribute("page");
		page.setSortName("createTime");
		page.setSortOrder("DESC");
		postss = postsService.getAllPosts(posts, page,getCurrentLanguage());
		
		System.out.println(page.getRowCount());
		ajaxResponseRenderer.addRender(dataZone);
	}
}
