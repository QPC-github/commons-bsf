/*
 * The Apache Software License, Version 1.1
 *
 * Copyright (c) 2002 The Apache Software Foundation.  All rights
 * reserved.
 *
 * Redistribution and use in source and binary forms, with or without
 * modification, are permitted provided that the following conditions
 * are met:
 *
 * 1. Redistributions of source code must retain the above copyright
 *    notice, this list of conditions and the following disclaimer.
 *
 * 2. Redistributions in binary form must reproduce the above copyright
 *    notice, this list of conditions and the following disclaimer in
 *    the documentation and/or other materials provided with the
 *    distribution.
 *
 * 3. The end-user documentation included with the redistribution, if
 *    any, must include the following acknowlegement:
 *       "This product includes software developed by the
 *        Apache Software Foundation (http://www.apache.org/)."
 *    Alternately, this acknowlegement may appear in the software itself,
 *    if and wherever such third-party acknowlegements normally appear.
 *
 * 4. The names "BSF", "Apache", and "Apache Software Foundation"
 *    must not be used to endorse or promote products derived from
 *    this software without prior written permission. For written
 *    permission, please contact apache@apache.org.
 *
 * 5. Products derived from this software may not be called "Apache"
 *    nor may "Apache" appear in their names without prior written
 *    permission of the Apache Group.
 *
 * THIS SOFTWARE IS PROVIDED ``AS IS'' AND ANY EXPRESSED OR IMPLIED
 * WARRANTIES, INCLUDING, BUT NOT LIMITED TO, THE IMPLIED WARRANTIES
 * OF MERCHANTABILITY AND FITNESS FOR A PARTICULAR PURPOSE ARE
 * DISCLAIMED.  IN NO EVENT SHALL THE APACHE SOFTWARE FOUNDATION OR
 * ITS CONTRIBUTORS BE LIABLE FOR ANY DIRECT, INDIRECT, INCIDENTAL,
 * SPECIAL, EXEMPLARY, OR CONSEQUENTIAL DAMAGES (INCLUDING, BUT NOT
 * LIMITED TO, PROCUREMENT OF SUBSTITUTE GOODS OR SERVICES; LOSS OF
 * USE, DATA, OR PROFITS; OR BUSINESS INTERRUPTION) HOWEVER CAUSED AND
 * ON ANY THEORY OF LIABILITY, WHETHER IN CONTRACT, STRICT LIABILITY,
 * OR TORT (INCLUDING NEGLIGENCE OR OTHERWISE) ARISING IN ANY WAY OUT
 * OF THE USE OF THIS SOFTWARE, EVEN IF ADVISED OF THE POSSIBILITY OF
 * SUCH DAMAGE.
 * ====================================================================
 *
 * This software consists of voluntary contributions made by many individuals
 * on behalf of the Apache Software Foundation and was originally created by
 * Sanjiva Weerawarana and others at International Business Machines
 * Corporation. For more information on the Apache Software Foundation,
 * please see <http://www.apache.org/>.
 */

package org.apache.bsf.debug.meta;

import java.io.*;
import java.net.*;
import org.apache.bsf.debug.*;
import org.apache.bsf.debug.jsdi.*;
import org.apache.bsf.debug.util.*;

public class JsCallbacksDispatcher extends Dispatcher {

	public JsCallbacksDispatcher(SocketConnection con) {
		super(con);
	}

	public void dispatch(ResultCell rcell) throws Exception {
		String filename, lang;
		int count, tid, uid;
		boolean bool;
		JsContext cx;
		Object obj;
		JsCallbacks self;
		Exception ex;

		self = (JsCallbacks) rcell.selfSkel;

		switch (rcell.methodId) {

			case DebugConstants.CB_POLL :
				rcell.booleanResult(true);
				break;

			case DebugConstants.CB_HANDLE_BREAKPOINT_HIT :
				cx = (JsContext) rcell.readObject();
				self.handleBreakpointHit(cx);
				rcell.voidResult();
				break;

			case DebugConstants.CB_HANDLE_ENGINE_STOPPED :
				cx = (JsContext) rcell.readObject();
				self.handleEngineStopped(cx);
				rcell.voidResult();
				break;

			case DebugConstants.CB_HANDLE_EXCEPTION_THROWN :
				cx = (JsContext) rcell.readObject();
				ex = (Exception) rcell.readObject();
				self.handleExceptionThrown(cx, ex);
				rcell.voidResult();
				break;

			case DebugConstants.CB_HANDLE_STEPPING_DONE :
				cx = (JsContext) rcell.readObject();
				self.handleSteppingDone(cx);
				rcell.voidResult();
				break;
		}
	}
}
