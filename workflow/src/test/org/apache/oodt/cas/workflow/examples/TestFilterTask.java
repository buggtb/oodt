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

package org.apache.oodt.cas.workflow.examples;

import org.apache.oodt.cas.metadata.Metadata;
import org.apache.oodt.cas.workflow.structs.WorkflowTaskConfiguration;
import org.apache.oodt.cas.workflow.structs.exceptions.WorkflowTaskInstanceException;

import junit.framework.TestCase;

/**
 * Test harness for FilterTask.
 * 
 */
public class TestFilterTask extends TestCase {

	private FilterTask task;
	private Metadata dynMet;
	private WorkflowTaskConfiguration config;
	private static final String prodDateTime = "2014-04-07T00:00:00.000Z";
	private static final String filename = "foo.txt";

	/*
	 * (non-Javadoc)
	 * 
	 * @see junit.framework.TestCase#setUp()
	 */
	@Override
	protected void setUp() throws Exception {
		super.setUp();
		task = new FilterTask();
		dynMet = new Metadata();
		dynMet.addMetadata("Filename", filename);
		dynMet.addMetadata("ProductionDateTime", prodDateTime);
		config = new WorkflowTaskConfiguration();
	}

	/*
	 * (non-Javadoc)
	 * 
	 * @see junit.framework.TestCase#tearDown()
	 */
	@Override
	protected void tearDown() throws Exception {
		super.tearDown();
		task = null;
		dynMet = null;
		config = null;
	}

	public void testRemove() {
		config.addConfigProperty("Remove_Key", "Filename");
		try {
			task.run(dynMet, config);
		} catch (WorkflowTaskInstanceException e) {
			e.printStackTrace();
			fail(e.getMessage());
		}
		assertNotNull(dynMet);
		assertFalse(dynMet.containsKey("Filename"));
	}

	public void testRename() {
		config.addConfigProperty("Rename_ProductionDateTime",
				"Prior_ProductionDateTime");
		try {
			task.run(dynMet, config);
		} catch (WorkflowTaskInstanceException e) {
			e.printStackTrace();
			fail(e.getMessage());
		}
		assertNotNull(dynMet);
		assertFalse(dynMet.containsKey("ProductionDateTime"));
		assertTrue(dynMet.containsKey("Prior_ProductionDateTime"));
		assertEquals(
				"Value: [" + dynMet.getMetadata("Prior_ProductionDateTime")
						+ "] was not equal to: [" + prodDateTime + "]",
				prodDateTime, dynMet.getMetadata("Prior_ProductionDateTime"));
	}

	public void testRemoveAfterRename() {
		config.addConfigProperty("Rename_Filename", "FooName");
		config.addConfigProperty("Remove_Key", "FooName");
		try {
			task.run(dynMet, config);
		} catch (WorkflowTaskInstanceException e) {
			e.printStackTrace();
			fail(e.getMessage());
		}
		assertNotNull(dynMet);
		assertFalse(dynMet.containsKey("Filename"));
		assertFalse(dynMet.containsKey("FooName"));
	}

}