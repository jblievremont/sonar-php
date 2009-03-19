/*
 * Sonar, open source software quality management tool.
 * Copyright (C) 2009 SonarSource SA
 * mailto:contact AT sonarsource DOT com
 *
 * Sonar is free software; you can redistribute it and/or
 * modify it under the terms of the GNU Lesser General Public
 * License as published by the Free Software Foundation; either
 * version 3 of the License, or (at your option) any later version.
 *
 * Sonar is distributed in the hope that it will be useful,
 * but WITHOUT ANY WARRANTY; without even the implied warranty of
 * MERCHANTABILITY or FITNESS FOR A PARTICULAR PURPOSE.  See the GNU
 * Lesser General Public License for more details.
 *
 * You should have received a copy of the GNU Lesser General Public
 * License along with Sonar; if not, write to the Free Software
 * Foundation, Inc., 51 Franklin Street, Fifth Floor, Boston, MA  02
 */

package org.sonar.plugins.php.phpcodesniffer;

import org.apache.commons.io.IOUtils;
import org.sonar.commons.Language;
import org.sonar.commons.rules.Rule;
import org.sonar.commons.rules.RulesProfile;
import org.sonar.plugins.api.rules.RulesRepository;
import org.sonar.plugins.api.rules.StandardRulesXmlParser;
import org.sonar.plugins.php.Php;

import java.io.InputStream;
import java.util.Collections;
import java.util.List;

public class PhpCodeSnifferRulesRepository implements RulesRepository {

  public Language getLanguage() {
    return new Php();
  }

  public List<Rule> getInitialReferential() {
    InputStream input = getClass().getResourceAsStream("/org/sonar/plugins/php/phpcodesniffer/rules.xml");
    try {
      return new StandardRulesXmlParser().parse(input);
    } finally {
      IOUtils.closeQuietly(input);
    }
  }

  public List<Rule> parseReferential(String fileContent) {
    return new StandardRulesXmlParser().parse(fileContent);
  }

  public List<RulesProfile> getProvidedProfiles() {
    return Collections.emptyList();
  }
}