/*
 * Copyright 2010 Daniel Kurka
 * 
 * Licensed under the Apache License, Version 2.0 (the "License"); you may not
 * use this file except in compliance with the License. You may obtain a copy of
 * the License at
 * 
 * http://www.apache.org/licenses/LICENSE-2.0
 * 
 * Unless required by applicable law or agreed to in writing, software
 * distributed under the License is distributed on an "AS IS" BASIS, WITHOUT
 * WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied. See the
 * License for the specific language governing permissions and limitations under
 * the License.
 */
package de.kurka.phonegap.client.notification;

/**
 * Visual, audible, and tactile device notifications.
 * 
 * @author Daniel Kurka
 * 
 */
public class NotificationMobileImpl implements Notification {

	private static final AlertCallback emptyCallback = new AlertCallback() {

		@Override
		public void onOkButtonClicked() {

		}
	};
	private static final String[] defaultLabels = new String[] { "Ok", "Cancel" };

	public NotificationMobileImpl() {

	}

	@Override
	public native void beep(int count)/*-{
		$wnd.navigator.notification.beep(count);
	}-*/;

	@Override
	public native void vibrate(int milliseconds)/*-{
		$wnd.navigator.notification.vibrate(milliseconds);
	}-*/;

	@Override
	public void alert(String message) {
		alert(message, emptyCallback);

	}

	@Override
	public void alert(String message, AlertCallback callback) {
		alert(message, callback, "Alert");

	}

	@Override
	public void alert(String message, AlertCallback callback, String title) {
		alert(message, callback, title, "Ok");

	}

	@Override
	public native void alert(String message, AlertCallback callback, String title, String buttonName) /*-{

		var cal = function() {
			callback.@de.kurka.phonegap.client.notification.AlertCallback::onOkButtonClicked()();
		};

		$wnd.navigator.notification.alert(message, $entry(cal), title,
				buttonName);

	}-*/;

	@Override
	public void confirm(String message, ConfirmCallback callback) {
		confirm(message, callback, "Title");

	}

	@Override
	public void confirm(String message, ConfirmCallback callback, String title) {

		confirm(message, callback, title, defaultLabels);

	}

	@Override
	public void confirm(String message, ConfirmCallback callback, String title, String[] buttonLabels) {
		if (buttonLabels == null)
			buttonLabels = defaultLabels;

		if (buttonLabels.length != 2) {
			throw new IllegalArgumentException("expected two labels for buttons got: " + buttonLabels.length);
		}

		String labels = buttonLabels[0] + "," + buttonLabels[1];
		confirm0(message, callback, title, labels);

	}

	private native void confirm0(String message, ConfirmCallback callback, String title, String buttonLabels) /*-{

		var cal = function(button) {
			callback.@de.kurka.phonegap.client.notification.ConfirmCallback::onConfirm(I)(button);
		};

		$wnd.navigator.notification.confirm(message, $entry(cal), title,
				buttonLabels);
	}-*/;
}
