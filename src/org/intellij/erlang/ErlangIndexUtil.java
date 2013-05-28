/*
 * Copyright 2012-2013 Sergey Ignatov
 *
 * Licensed under the Apache License, Version 2.0 (the "License");
 * you may not use this file except in compliance with the License.
 * You may obtain a copy of the License at
 *
 * http://www.apache.org/licenses/LICENSE-2.0
 *
 * Unless required by applicable law or agreed to in writing, software
 * distributed under the License is distributed on an "AS IS" BASIS,
 * WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied.
 * See the License for the specific language governing permissions and
 * limitations under the License.
 */

package org.intellij.erlang;

import com.intellij.openapi.fileTypes.FileType;
import com.intellij.openapi.vfs.VirtualFile;
import com.intellij.util.indexing.FileBasedIndex;

/**
 * @author ignatov
 */
public class ErlangIndexUtil {
  public static final FileBasedIndex.InputFilter ERLANG_INPUT_FILTER = new FileBasedIndex.InputFilter() {
    @Override
    public boolean acceptInput(VirtualFile file) {
      return isErlangFileType(file.getFileType());
    }
  };

  static boolean isErlangFileType(FileType fileType) {
    return fileType == ErlangFileType.MODULE || fileType == ErlangFileType.HEADER;
  }
  
  static boolean isErlangModuleFileType(FileType fileType) {
    return fileType == ErlangFileType.MODULE;
  }
}
