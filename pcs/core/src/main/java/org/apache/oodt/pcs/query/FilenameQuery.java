/**
 * Licensed to the Apache Software Foundation (ASF) under one or more
 * contributor license agreements.  See the NOTICE file distributed with
 * this work for additional information regarding copyright ownership.
 * The ASF licenses this file to You under the Apache License, Version 2.0
 * (the "License"); you may not use this file except in compliance with
 * the License.  You may obtain a copy of the License at
 *
 *     http://www.apache.org/licenses/LICENSE-2.0
 *
 * Unless required by applicable law or agreed to in writing, software
 * distributed under the License is distributed on an "AS IS" BASIS,
 * WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied.
 * See the License for the specific language governing permissions and
 * limitations under the License.
 */

package org.apache.oodt.pcs.query;

//OODT imports
import org.apache.oodt.pcs.util.FileManagerUtils;
import org.apache.oodt.cas.filemgr.structs.Query;
import org.apache.oodt.cas.filemgr.structs.TermQueryCriteria;

/**
 * 
 * A query to the File Manager using the <code>Filename</code> product met field.
 * 
 * @author mattmann
 * @version $Revision$
 */
public class FilenameQuery extends AbstractPCSQuery {

  /* the product name that we're querying for */
  private String fileName = null;

  public FilenameQuery(String name, FileManagerUtils fm) {
    super(fm);
    this.fileName = name;
  }

  /**
   * @return the fileName
   */
  public String getFileName() {
    return fileName;
  }

  /**
   * @param fileName
   *          the fileName to set
   */
  public void setFileName(String fileName) {
    this.fileName = fileName;
  }

  /*
   * (non-Javadoc)
   * 
   * @see org.apache.oodt.pcs.query.PCSQuery#buildQuery()
   */
  public Query buildQuery() {
    Query query = new Query();
    TermQueryCriteria crit = new TermQueryCriteria(FILENAME, this.fileName);
    query.addCriterion(crit);
    return query;
  }

}
