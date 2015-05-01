/* 
 * RelatedVideoApplication.java
 * 
 * Copyright (c) 2012 Noterik B.V.
 * 
 * This file is part of Lou, related to the Noterik Springfield project.
 * It was created as a example of how to use the multiscreen toolkit
 *
 * RelatedVideo app is free software: you can redistribute it and/or modify
 * it under the terms of the GNU General Public License as published by
 * the Free Software Foundation, either version 3 of the License, or
 * (at your option) any later version.
 *
 * RelatedVideo app is distributed in the hope that it will be useful,
 * but WITHOUT ANY WARRANTY; without even the implied warranty of
 * MERCHANTABILITY or FITNESS FOR A PARTICULAR PURPOSE.  See the
 * GNU General Public License for more details.
 *
 * You should have received a copy of the GNU General Public License
 * along with RelatedVideo app.  If not, see <http://www.gnu.org/licenses/>.
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
public class RelatedvideoApplication extends Html5Application {

	public RelatedvideoApplication(String id) {
		super(id);
	}

	public Hashtable<Integer, String> heshfilter = new Hashtable<Integer, String>();
	private String platformFilter = "Europeana";

	public void searchvideo(Screen s) {
		String url = "/domain/espace/user/luce/video/EUS_14D168BAE77F6236F8FDC47D64EFE807";
		System.out.println("VIDEO ID: " + url);
		s.setProperty("videoid", url);
		s.setProperty("filter", "ep_videos");

		String videoPath = "/domain/espace/user/luce/video/EUS_14D168BAE77F6236F8FDC47D64EFE807";
		FsNode videonode = Fs.getNode(videoPath);
		if (videonode != null) {
			String genreProprty = videonode.getProperty("genre");
			String terms = videonode.getProperty("ThesaurusTerm");
			String title = videonode.getProperty("TitleSet_TitleSetInEnglish_title");
			String split[] = title.split(" ");
			String titleKeywords = "";

			for (int i = 0; i < split.length; i++) {
				String word = split[i];
				System.out.println("Title key word: " + word + "!");

				if (word.length() > 3) {
					titleKeywords += word + " ";
				}
			}
			heshfilter.put(1, genreProprty);
			heshfilter.put(2, terms);
			heshfilter.put(3, titleKeywords);
		}

		String result = setAlbrightFilter(s);

		if (result != null) {
			String videobuild = "";
			FsNode rawvideonode = Fs.getNode(videoPath + "/rawvideo/1");
			if (rawvideonode != null) {
				videobuild += "<video id=\"video1\" controls preload=\"none\" data-setup=\"{}\">";
				String mounts[] = rawvideonode.getProperty("mount").split(",");
				String mount = mounts[0];
				videoPath = videoPath.replace("espace", "euscreenxl").replace(
						"luce", "eu_luce");
				if (mount.indexOf("http://") == -1
						&& mount.indexOf("rtmp://") == -1) {
					String ap = "http://" + mount + ".noterik.com/progressive/"
							+ mount + videoPath + "/rawvideo/1/raw.mp4";
					videobuild += "<source src=\"" + ap
							+ "\" type=\"video/mp4\" /></video>";
				}
			}
			s.setContent("player", videobuild);
		}
		renderAlbrigthResults(s, result);
	}

	public void contentToProperties(Screen s, String content) {
		String[] cmd = content.split(",");
		for (int i = 0; i < cmd.length; i++) {
			String[] param = cmd[i].split("=");
			s.setProperty(param[0], param[1]);
		}
	}

	public String setAlbrightFilter(Screen s) {
		String url = "/domain/espace/user/luce/video/EUS_14D168BAE77F6236F8FDC47D64EFE807";
		String filter = (String) s.getProperty("filter");
		url = url + "/" + filter + "/";
		String fsxml = "<fsxml><properties><keywords>";

		for (Integer key : heshfilter.keySet()) {
			fsxml += heshfilter.get(key) + " ";
		}
		fsxml += "</keywords></properties></fsxml>";
		ServiceInterface albright = ServiceManager.getService("albright");
		String result = null;
		if (albright == null) {
			String msg = "Service Albright not found";
			s.setContent("defaultoutput", msg);
		} else {
			result = albright.get(url, fsxml, "text/xml");
			if (result == null) {
				s.setContent("defaultoutput", "<h2>No such " + "</h2>");
			}
		}
		return result;
	}

	public void renderAlbrigthResults(Screen s, String results) {
		FSList nodes = new FSList().parseNodes(results);
		if (nodes.size() > 0) {
			String body = "";
			for (Iterator<FsNode> iter = nodes.getNodes().iterator(); iter
					.hasNext();) {
				FsNode n = (FsNode) iter.next();
				body += "<div class='item'>";
				String thumbnail = n.getProperty("thumbnail");
				String objurl = n.getProperty("url");
				if (thumbnail != null) {
					body += "<a href='" + objurl + "' target=\"_blank\">";
					body += "<li><img src=\"" + thumbnail + "\"></li>";

					body += "<img src='" + thumbnail + "' />";
					body += "</a>";
				}
				body += "</div>";
			}

			body += "</ul>" + "</div>" + "</div>";
			if (this.platformFilter.equals("Europeana")) {
				s.setContent("defaultoutput", body);
			}
		} else {
			if (this.platformFilter.equals("Europeana")) {
				s.setContent("defaultoutput", "<h2>No related items</h2>");
			}
		}
	}
}
