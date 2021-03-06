/*
 * SonarQube PHP Plugin
 * Copyright (C) 2010 SonarSource and Akram Ben Aissi
 * dev@sonar.codehaus.org
 *
 * This program is free software; you can redistribute it and/or
 * modify it under the terms of the GNU Lesser General Public
 * License as published by the Free Software Foundation; either
 * version 3 of the License, or (at your option) any later version.
 *
 * This program is distributed in the hope that it will be useful,
 * but WITHOUT ANY WARRANTY; without even the implied warranty of
 * MERCHANTABILITY or FITNESS FOR A PARTICULAR PURPOSE.  See the GNU
 * Lesser General Public License for more details.
 *
 * You should have received a copy of the GNU Lesser General Public
 * License along with this program; if not, write to the Free Software
 * Foundation, Inc., 51 Franklin Street, Fifth Floor, Boston, MA  02
 */
package org.sonar.php.checks;

import com.sonar.sslr.api.AstNode;
import com.sonar.sslr.api.Grammar;
import org.sonar.check.Priority;
import org.sonar.check.Rule;
import org.sonar.php.api.PHPPunctuator;
import org.sonar.squidbridge.checks.SquidCheck;

@Rule(
  key = "S1106",
  priority = Priority.MAJOR)
public class LeftCurlyBraceStartsLineCheck extends SquidCheck<Grammar> {

  @Override
  public void init() {
    subscribeTo(PHPPunctuator.LCURLYBRACE);
  }

  @Override
  public void visitNode(AstNode astNode) {
    if (!isOnSameLineThanRightCurlyBrace(astNode) && !isFirstOnLine(astNode)) {
      getContext().createLineViolation(this, "Move this open curly brace to the beginning of next line.", astNode);
    }
  }

  private static boolean isFirstOnLine(AstNode lcurly) {
    return lcurly.getPreviousAstNode().getLastToken().getLine() != lcurly.getTokenLine();
  }

  private static boolean isOnSameLineThanRightCurlyBrace(AstNode lcurly) {
    return lcurly.getParent().getFirstChild(PHPPunctuator.RCURLYBRACE).getTokenLine() == lcurly.getTokenLine();
  }

}
