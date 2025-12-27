package com.example.notes.service;

import org.commonmark.node.*;
import org.commonmark.parser.Parser;
import org.commonmark.renderer.html.HtmlRenderer;
import org.springframework.stereotype.Service;

@Service
public class MarkdownService {

	public String convertMarkdownToHtml(String markdown) {
		Node document = getNode(markdown);
		HtmlRenderer renderer = HtmlRenderer.builder().build();
		return renderer.render((Node) document);
	}
	
	public static Node getNode(String markdown) {
		Parser parser = Parser.builder().build();
		Node document = parser.parse(markdown);
		return document;
	}
}
