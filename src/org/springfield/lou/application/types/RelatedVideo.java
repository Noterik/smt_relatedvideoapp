/* 
* EnrichermonitorApplication.java
* 
* Copyright (c) 2012 Noterik B.V.
* 
* This file is part of Lou, related to the Noterik Springfield project.
* It was created as a example of how to use the multiscreen toolkit
*
* Enrichermonitor app is free software: you can redistribute it and/or modify
* it under the terms of the GNU General Public License as published by
* the Free Software Foundation, either version 3 of the License, or
* (at your option) any later version.
*
* Enrichermonitor app is distributed in the hope that it will be useful,
* but WITHOUT ANY WARRANTY; without even the implied warranty of
* MERCHANTABILITY or FITNESS FOR A PARTICULAR PURPOSE.  See the
* GNU General Public License for more details.
*
* You should have received a copy of the GNU General Public License
* along with Enrichermonitor app.  If not, see <http://www.gnu.org/licenses/>.
*/
package org.springfield.lou.application.types;
import java.util.Hashtable;
import java.util.Iterator;

import org.springfield.fs.FSList;
import org.springfield.fs.Fs;
import org.springfield.fs.FsNode;
import org.springfield.lou.application.*;
import org.springfield.lou.screen.*;
import org.springfield.mojo.interfaces.ServiceInterface;
import org.springfield.mojo.interfaces.ServiceManager;

public class RelatedVideo extends Html5Application{
	
 	public RelatedVideo(String id) {
		super(id); 
	}
 	
	public Hashtable<Integer, String> heshfilter = new Hashtable<Integer, String>();
	private String platformFilter = "Europeana";
	private String searchKeysForPlatformFilter;
 	private String videobuildforplatform;
 	public void decadefilter(Screen s,String content) {
 		System.out.println("decadeFilter:" +content);
 		String decade = content;
 		
 		String videoPath = (String)s.getProperty("videoid");
		FsNode videonode = Fs.getNode(videoPath);
		if(videonode!=null) { //Build the search terms html
			String genreProprty = videonode.getProperty("genre");
			String terms = videonode.getProperty("ThesaurusTerm");
			String title = videonode.getProperty("TitleSet_TitleSetInEnglish_title");
			String split[]= title.split(" ");
			String titleKeywords = "";

			for(int i = 0; i < split.length; i++) {
				String word = split[i];
				System.out.println("Title key word: " + word + "!");
				
				if(word.length()>3){
					titleKeywords += word + " ";
				}	
				
			}
			heshfilter.put(1, genreProprty);
			heshfilter.put(2, terms);
			heshfilter.put(3, titleKeywords);
			heshfilter.put(4, decade);
		}
 		String result =  setAlbrightFilter(s);
 		
 		
 		
 		
 		if(result!=null) {
			if(videonode!=null) {
				String genreProprty = videonode.getProperty("genre");
				String terms = videonode.getProperty("ThesaurusTerm");
				String title = videonode.getProperty("TitleSet_TitleSetInEnglish_title");
				String split[]= title.split(" ");
				String titleKeywords = "";

				for(int i = 0; i < split.length; i++) {
					String word = split[i];
					System.out.println("Title key word: " + word + "!");
					
					if(word.length()>3){
						titleKeywords += word + " ";
					}	
					
				}
				terms = terms.replaceAll(","," "); //Terms can be multiple separated by comma, so replace comma with space
				String all = genreProprty + " " + terms + " " + decade;
			
				String body = "<div class=\"qtitle\">Query Structure:</div>";
				body += "<div class=\"rul1\"><ul><li class=\"labell\"><label>Theme1: </label></li><li class=\"valuee\">"+ genreProprty +"</li><li class=\"ex\"><a href=\"#\" class=\"dismiss\" id=\"1\">X</a></li></ul></div>";
				body += "<div class=\"rul1\"><ul><li class=\"labell\"><label>Topic: </label></li><li class=\"valuee\">"+ terms +"</li><li class=\"ex\"><a href=\"#\" class=\"dismiss\" id=\"2\">X</a></li></ul></div>";
				body += "<div class=\"rul1\"><ul><li class=\"labell\"><label>Keywords from title: </label></li><li class=\"valuee\">"+ titleKeywords +"</li><li class=\"ex\"><a href=\"#\" class=\"dismiss\" id=\"3\">X</a></li></ul></div>";
				body += "<div class=\"rul1\"><ul><li class=\"labell\"><label>Decade: </label></li><li class=\"valuee\">"+ decade +"</li><li class=\"ex\"><a href=\"#\" class=\"dismiss\" id=\"4\">X</a></li></ul></div>";
				body += "<div class=\"rul2\"><button type=\"button\">Save</button></div>"; 
				loadContent(s, "searchkeys");
				this.searchKeysForPlatformFilter = body;
				s.setContent("searchkeys", body);
				s.putMsg("searchkeys", "", "initEvents()");
			}

			renderAlbrigthResults(s, result);
		}
 	}
 	
 	public void filetypefilter(Screen s,String content) {
 		System.out.println("filetypefilter:" +content);
 		String filter = content;
 		s.setProperty("filter", filter);
 		
 		String videoPath = (String)s.getProperty("videoid");
		FsNode videonode = Fs.getNode(videoPath);
		if(videonode!=null) { //Build the search terms html
			String genreProprty = videonode.getProperty("genre");
			String terms = videonode.getProperty("ThesaurusTerm");
			String title = videonode.getProperty("TitleSet_TitleSetInEnglish_title");
			String split[]= title.split(" ");
			String titleKeywords = "";

			for(int i = 0; i < split.length; i++) {
				String word = split[i];
				System.out.println("Title key word: " + word + "!");
				
				if(word.length()>3){
					titleKeywords += word + " ";
				}	
				
			}
			heshfilter.put(1, genreProprty);
			heshfilter.put(2, terms);
			heshfilter.put(3, titleKeywords);
		}
 		
 		String result =  setAlbrightFilter(s);
		
 		
		if(result != null) { // Build up related result
				String filterType = "Video";
				if(filter.equals("ep_video")){
					 filterType = "Video";
				}else if(filter.equals("ep_images")){
					 filterType = "Image";
				}
				if(videonode!=null) {
					String genreProprty = videonode.getProperty("genre");
					String terms = videonode.getProperty("ThesaurusTerm");
					String title = videonode.getProperty("TitleSet_TitleSetInEnglish_title");
					String split[]= title.split(" ");
					String titleKeywords = "";

					for(int i = 0; i < split.length; i++) {
						String word = split[i];
						System.out.println("Title key word: " + word + "!");
						
						if(word.length()>3){
							titleKeywords += word + " ";
						}	
						
					}
					 
					terms = terms.replaceAll(","," "); //Terms can be multiple separated by comma, so replace comma with space

					String body = "<div class=\"qtitle\">"+filterType+" query structure:</div>";
					body += "<div class=\"rul1\"><ul><li class=\"labell\"><label>Theme2: </label></li><li class=\"valuee\">"+ genreProprty +"</li><li class=\"ex\"><a href=\"#\" class=\"dismiss\" id=\"1\">X</a></li></ul></div>";
					body += "<div class=\"rul1\"><ul><li class=\"labell\"><label>Topic: </label></li><li class=\"valuee\">"+ terms +"</li><li class=\"ex\"><a href=\"#\" class=\"dismiss\" id=\"2\">X</a></li></ul></div>";
					body += "<div class=\"rul1\"><ul><li class=\"labell\"><label>Keywords from title: </label></li><li class=\"valuee\">"+ titleKeywords +"</li><li class=\"ex\"><a href=\"#\" class=\"dismiss\" id=\"3\">X</a></li></ul></div>";
					body += "<div class=\"rul2\"><button type=\"button\">Save</button></div>";
					loadContent(s, "searchkeys");
					this.searchKeysForPlatformFilter = body;
					s.setContent("searchkeys", body);
					s.putMsg("searchkeys", "", "initEvents()");

				}
				
				renderAlbrigthResults(s, result);
			}
 	}
    
    public void searchvideo(Screen s,String content) {
    	System.out.println("searchVideo:" +content);
		contentToProperties(s,content);
		String url = (String)s.getProperty("videouri.value");
		System.out.println("VIDEO ID: " + url);
		s.setProperty("videoid", url);
		s.setProperty("filter", "ep_images");
		
		String videoPath = (String)s.getProperty("videouri.value");
		FsNode videonode = Fs.getNode(videoPath);
		if(videonode!=null) { //Build the search terms html
			String genreProprty = videonode.getProperty("genre");
			String terms = videonode.getProperty("ThesaurusTerm");
			String title = videonode.getProperty("TitleSet_TitleSetInEnglish_title");
			String split[]= title.split(" ");
			String titleKeywords = "";

			for(int i = 0; i < split.length; i++) {
				String word = split[i];
				System.out.println("Title key word: " + word + "!");
				
				if(word.length()>3){
					titleKeywords += word + " ";
				}	
				
			}
			 heshfilter.put(1, genreProprty);
			 heshfilter.put(2, terms);
			 heshfilter.put(3, titleKeywords);
		}
		
		String result =  setAlbrightFilter(s);

		if(result != null) { // Build up related result
				
				String videobuild = "";
				loadContent(s, "platformfilter");
				loadContent(s, "filetypefilter");
				loadContent(s, "decadefilter");
								
				if(videonode!=null) {
					String title = videonode.getProperty("TitleSet_TitleSetInEnglish_title");
					String orgtitle = videonode.getProperty("TitleSet_TitleSetInOriginalLanguage_title");
					String year = videonode.getProperty("SpatioTemporalInformation_TemporalInformation_productionYear");
					String language = videonode.getProperty("originallanguage");
					String decades = videonode.getProperty("decade");
					videobuild += "<div class=\"descriptionleft\">";
					videobuild += "<p class=\"t2\">"+title+"</p>";
					videobuild += "<p id=\"t1\">ORIGINAL TITLE</p>";
					videobuild += "<p>"+orgtitle+"</p>";
					videobuild += "<p id=\"t1\">PRODUCTION YEAR</p>";
					videobuild += "<p>"+year+"</p>";
					videobuild += "<p id=\"t1\">LANGUAGE</p>";
					videobuild += "<p>"+language+"</p>";
					videobuild += "</div>";	
				}											
				
				
				// if it's a video we need it's rawvideo node for where the file is.
				FsNode rawvideonode = Fs.getNode(videoPath+"/rawvideo/1");
				if (rawvideonode!=null) {
					// Build the video preview
					videobuild += "<video id=\"video1\" controls preload=\"none\" data-setup=\"{}\">";
					String mounts[] = rawvideonode.getProperty("mount").split(",");
					
					// based on the type of mount (path) create the rest of the video tag.
					String mount = mounts[0];
					// Fixed the videoPath so that it get the original video from EuscreenXL domain
					videoPath = videoPath.replace("espace", "euscreenxl").replace("luce", "eu_luce");
					if (mount.indexOf("http://")==-1 && mount.indexOf("rtmp://")==-1) {
						String ap = "http://"+mount+".noterik.com/progressive/"+mount+videoPath+"/rawvideo/1/raw.mp4";
						videobuild+="<source src=\""+ap+"\" type=\"video/mp4\" /></video>";
					}

				}
				
				if(videonode!=null) {
					
					String sum = videonode.getProperty("summary");
					String series = videonode.getProperty("TitleSet_TitleSetInEnglish_seriesOrCollectionTitle");
					
					videobuild += "<div class=\"descriptionright\">";
					videobuild += "<p id=\"t1\">SUMMARY</p>";
					videobuild += "<p>"+sum+"</p>";
					videobuild += "<p id=\"t1\">THIS ITEM IS PART OF THE SERIES/COLLECTION</p>";
					videobuild += "<p>"+series+"</p>";
					videobuild += "</div>";	
				}
				
				this.videobuildforplatform = videobuild;
				
				
				if(this.platformFilter.equals("Europeana")){
					s.setContent("player", videobuild);
					
					System.out.println(this.platformFilter);
				}else{
					System.out.println(this.platformFilter);
					s.setContent("player", "THIS IS SPARTA I NE SE PODURJA KOPELE");
				}
				
				
				if(videonode!=null) { //Build the search terms html
					String genreProprty = videonode.getProperty("genre");
					String terms = videonode.getProperty("ThesaurusTerm");
					String title = videonode.getProperty("TitleSet_TitleSetInEnglish_title");
					String split[]= title.split(" ");
					String titleKeywords = "";

					for(int i = 0; i < split.length; i++) {
						String word = split[i];
						System.out.println("Title key word: " + word + "!");
						
						if(word.length()>3){
							titleKeywords += word + " ";
						}	
						
					}
				
					 System.out.println("FILTER HASH TABLE" + heshfilter);
					
					titleKeywords = titleKeywords.trim();
					
					String body = "<div class=\"qtitle\">Image query structure:</div>";
					body += "<div class=\"rul1\"><ul><li class=\"labell\"><label>Theme: </label></li><li class=\"valuee\">"+ genreProprty +"</li><li class=\"ex\"><a href=\"#\" class=\"dismiss\" id=\"1\">X</a></li></ul></div>";
					body += "<div class=\"rul1\"><ul><li class=\"labell\"><label>Topic: </label></li><li class=\"valuee\">"+ terms +"</li><li class=\"ex\"><a href=\"#\" class=\"dismiss\" id=\"2\">X</a></li></ul></div>";
					body += "<div class=\"rul1\"><ul><li class=\"labell\"><label>Keywords from title: </label></li><li class=\"valuee\">"+ titleKeywords +"</li><li class=\"ex\"><a href=\"#\" class=\"dismiss\" id=\"3\">X</a></li></ul></div>";
					body += "<div class=\"rul2\"><button type=\"button\" id=\"testSave\">Save</button></div>";
					loadContent(s, "searchkeys");
					this.searchKeysForPlatformFilter = body;
					s.setContent("searchkeys", body);
					s.putMsg("searchkeys", "", "initEvents()");
				}	
				
				renderAlbrigthResults(s, result);
			}
	}

   
    public void contentToProperties(Screen s,String content) {
		String[] cmd=content.split(",");
		for (int i=0;i<cmd.length;i++) {
			String[] param = cmd[i].split("=");
			s.setProperty(param[0],param[1]);
		}
		
	}
    
    public void removeFilters(Screen s, String str){
        System.out.println("----------------------------------------------------------------------------");
 	    System.out.println("ITEM WITH ID: " + str + " HAS BEEN REMOVED");
 	    heshfilter.remove(Integer.parseInt(str));
 	    System.out.println(heshfilter);
 	    String result = setAlbrightFilter(s);
 	    renderAlbrigthResults(s, result);
    }


    
    public String setAlbrightFilter(Screen s){
    	
    	String url = (String)s.getProperty("videoid");
    	String filter = (String)s.getProperty("filter");
		url = url + "/" + filter + "/";
    	
		String fsxml = "<fsxml><properties><keywords>";
		
		for (Integer key : heshfilter.keySet()) {
			fsxml +=  heshfilter.get(key) + " ";
		}
		
		fsxml += "</keywords></properties></fsxml>";
		
        ServiceInterface albright = ServiceManager.getService("albright");
        System.out.println("----------------------------------------------------------------------------");
        String result = null;
        if (albright==null) {
			String msg = "Service Albright not found";
			s.setContent("defaultoutput", msg);
		} else {
			result = albright.get(url,fsxml,"text/xml");
			if(result==null) { //No video
				s.setContent("defaultoutput", "<h2>No such " + filter + "</h2>");
			}
		}
        System.out.println("DAS IST OUR RESULT: " + result);
        return result;
    }
    
    public void renderAlbrigthResults(Screen s, String results) {
    	// result contains the <fsxml> it must be parsed
		FSList nodes = new FSList().parseNodes(results);

		// if there's a thumbnail property in the node you can make html and show it in defaultoutput
		
		System.out.println("NDOE SIZE DIVCODE="+nodes.size());
		if(nodes.size()>0) {  //checks if there are related items
			// Loop through all the nodes
			// Available properties are :
			/*
			 * title
			 * url
			 * creator
			 * thumbnail
			 * provider
			 */
			String body = "<div id=\"slider1\">"
					+ "<a class=\"buttons prev\" href=\"#\">&#60;</a>"
					+ "<div class=\"viewport\">"
					+ "<ul class=\"overview\">";
			
			for(Iterator<FsNode> iter = nodes.getNodes().iterator() ; iter.hasNext(); ) {
				FsNode n = (FsNode)iter.next(); 
				// body += "<div class='item'>";
				String thumbnail = n.getProperty("thumbnail");
				String objurl = n.getProperty("url");
				
				if(thumbnail!=null) { // Check if there is a thumbnail
					// body += "<a href='" + objurl + "' target=\"_blank\">";
					body += "<li><img src=\""+thumbnail+"\"></li>";

					// body += "<img src='" + thumbnail + "' />";
					// body += "</a>";
				}
			   // body += "</div>";
			}
			body += "</ul>" 
					+"</div>"
					+ "<a class=\"buttons next\" href=\"#\">&#62;</a>" 
					+ "</div>";
			if(this.platformFilter.equals("Europeana")){
				s.setContent("defaultoutput", body);
			}else{
				s.setContent("defaultoutput", "<div class=\"noGoogle\">The functionality is not supported yet</div>");
			}
		}	else {
			if(this.platformFilter.equals("Europeana")){
				s.setContent("defaultoutput", "<h2>No related items</h2>");
			}else {
				s.setContent("defaultoutput", "<div class=\"noGoogle\">The functionality is not supported yet</div>");

			}
		}
    }
    public void platformfilter(Screen s, String val)
    {
    	this.platformFilter = val;
    	
    	if(this.platformFilter.equals("Europeana")){
    		loadContent(s, "searchkeys");
			System.out.println(this.platformFilter);
			s.setContent("player", this.videobuildforplatform);
			//s.setContent("searchkeays", this.searchKeysForPlatformFilter);
		}else {
			s.setContent("player", "<div class=\"noGoogle\">The functionality is not supported yet</div>");
			s.removeContent("searchkeys");
			removeContent(s, "searchkeys");
		}
    	System.out.println(val);
    }
}
