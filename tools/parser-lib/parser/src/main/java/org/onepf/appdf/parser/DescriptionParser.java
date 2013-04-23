/*******************************************************************************
 * Copyright 2012 One Platform Foundation
 * 
 *    Licensed under the Apache License, Version 2.0 (the "License");
 *    you may not use this file except in compliance with the License.
 *    You may obtain a copy of the License at
 * 
 *        http://www.apache.org/licenses/LICENSE-2.0
 * 
 *    Unless required by applicable law or agreed to in writing, software
 *    distributed under the License is distributed on an "AS IS" BASIS,
 *    WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied.
 *    See the License for the specific language governing permissions and
 *    limitations under the License.
 ******************************************************************************/
package org.onepf.appdf.parser;

import org.onepf.appdf.model.Application;
import org.onepf.appdf.model.Description;
import org.onepf.appdf.model.LanguageCatalog;
import org.w3c.dom.NamedNodeMap;
import org.w3c.dom.Node;

public class DescriptionParser  implements NodeParser<Application> {
	
	
    private static final class InnerTagsParser extends BaseParser<Description, DescriptionTag>{

        public InnerTagsParser() {
            super(DescriptionTag.class, "description");         
        }
        
    }
    
	private static final String LANGUAGE_ATTR_NAME = "language";
	private final boolean isMain;
	
	public DescriptionParser(boolean isMain) {
	    this.isMain = isMain;
	}
	
	
	public void parse(Node node,Application application){
		Description description = new Description();
		NamedNodeMap attributes = node.getAttributes();
		Node languageAttr = attributes.getNamedItem(LANGUAGE_ATTR_NAME);
        if (languageAttr != null) {
            String language = languageAttr.getNodeValue();
            description.setLanguage(LanguageCatalog.INSTANCE.getByCode(language));
        }
        (new InnerTagsParser()).parse(node, description);
		if(isMain){
		    application.setMainDescription(description);
		}else{
		    application.addDescriptionLocalisation(description);
		}
	}

}
