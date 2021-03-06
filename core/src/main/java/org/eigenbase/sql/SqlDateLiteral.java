/*
// Licensed to Julian Hyde under one or more contributor license
// agreements. See the NOTICE file distributed with this work for
// additional information regarding copyright ownership.
//
// Julian Hyde licenses this file to you under the Apache License,
// Version 2.0 (the "License"); you may not use this file except in
// compliance with the License. You may obtain a copy of the License at:
//
// http://www.apache.org/licenses/LICENSE-2.0
//
// Unless required by applicable law or agreed to in writing, software
// distributed under the License is distributed on an "AS IS" BASIS,
// WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied.
// See the License for the specific language governing permissions and
// limitations under the License.
*/
package org.eigenbase.sql;

import java.util.*;

import org.eigenbase.reltype.*;
import org.eigenbase.sql.parser.*;
import org.eigenbase.sql.type.*;

/**
 * A SQL literal representing a DATE value, such as <code>DATE
 * '2004-10-22'</code>.
 *
 * <p>Create values using {@link SqlLiteral#createDate}.
 */
public class SqlDateLiteral extends SqlAbstractDateTimeLiteral {
  //~ Constructors -----------------------------------------------------------

  SqlDateLiteral(Calendar d, SqlParserPos pos) {
    super(d, false, SqlTypeName.DATE, 0, SqlParserUtil.DateFormatStr, pos);
  }

  SqlDateLiteral(Calendar d, String format, SqlParserPos pos) {
    super(d, false, SqlTypeName.DATE, 0, format, pos);
  }

  //~ Methods ----------------------------------------------------------------

  public SqlNode clone(SqlParserPos pos) {
    return new SqlDateLiteral((Calendar) value, pos);
  }

  public String toString() {
    return "DATE '" + toFormattedString() + "'";
  }

  /**
   * Returns e.g. '1969-07-21'.
   */
  public String toFormattedString() {
    return getDate().toString(formatString);
  }

  public RelDataType createSqlType(RelDataTypeFactory typeFactory) {
    return typeFactory.createSqlType(getTypeName());
  }

  public void unparse(
      SqlWriter writer,
      int leftPrec,
      int rightPrec) {
    switch (writer.getDialect().getDatabaseProduct()) {
    case MSSQL:
      writer.literal("'" + this.toFormattedString() + "'");
      break;
    default:
      writer.literal(this.toString());
      break;
    }
  }
}

// End SqlDateLiteral.java
