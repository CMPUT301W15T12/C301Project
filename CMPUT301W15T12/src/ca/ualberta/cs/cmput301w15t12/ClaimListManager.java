/*
Travel App: Keeps tracks of expenses and claims for various trips.

Copyright [2015] Sarah Van Belleghem vanbelle@ualberta.ca
Licensed under the Apache License, Version 2.0 (the "License");
you may not use this file except in compliance with the License.
You may obtain a copy of the License at

    http://www.apache.org/licenses/LICENSE-2.0

Unless required by applicable law or agreed to in writing, software
distributed under the License is distributed on an "AS IS" BASIS,
WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied.
See the License for the specific language governing permissions and
limitations under the License.
*/

package ca.ualberta.cs.cmput301w15t12;

import java.io.ByteArrayInputStream;
import java.io.ByteArrayOutputStream;
import java.io.IOException;
import java.io.ObjectInputStream;
import java.io.ObjectOutputStream;

import android.content.Context;
import android.content.SharedPreferences;
import android.content.SharedPreferences.Editor;
import android.util.Base64;

//stores the claim list in memory

	public class ClaimListManager {
		static final String prefFile = "ClaimList";
		static final String clKey = "claimList";
		Context context;
		
		static private ClaimListManager claimListManager = null;
		
		public static void initManager(Context context) {
			if (claimListManager == null) {
				if (context==null) {
					throw new RuntimeException("missing context for ClaimListManager");
				}
				claimListManager = new ClaimListManager(context);
			}
		}
		
		public static ClaimListManager getManager() {
			if (claimListManager==null) {
				throw new RuntimeException("Did not initialize Manager");
			}
			return claimListManager;
		}
		
		public ClaimListManager(Context context) {
			this.context = context;
		}
		
		public ClaimList loadClaimList() throws ClassNotFoundException, IOException {
			SharedPreferences settings = context.getSharedPreferences(prefFile, Context.MODE_PRIVATE);
			String claimListData = settings.getString(clKey, "");	
			if (claimListData.equals("")) {
				return new ClaimList();
			} else {	
				return claimListFromString(claimListData);
			}
		}
		
		static public ClaimList claimListFromString(String claimListData) throws ClassNotFoundException, IOException {
			ByteArrayInputStream bi = new ByteArrayInputStream(Base64.decode(claimListData, Base64.DEFAULT));
			ObjectInputStream oi = new ObjectInputStream(bi);
			return (ClaimList)oi.readObject();
		}
		
		static public String claimListToString(ClaimList cl) throws IOException {
			ByteArrayOutputStream bo = new ByteArrayOutputStream();
			ObjectOutputStream oo = new ObjectOutputStream(bo);
			oo.writeObject(cl);
			oo.close();
			byte bytes[] = bo.toByteArray();
			return Base64.encodeToString(bytes,Base64.DEFAULT);
		}
		
		public void saveClaimList(ClaimList cl) throws IOException {
			SharedPreferences settings = context.getSharedPreferences(prefFile, Context.MODE_PRIVATE);
			Editor editor = settings.edit();
			editor.putString(clKey, claimListToString(cl));
			editor.commit();	
		}

}
