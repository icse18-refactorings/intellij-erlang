/*
 * Copyright 2012 Sergey Ignatov
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

/*
 * Copyright 2012 Sergey Ignatov
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

package org.intellij.erlang.psi.impl;

import com.intellij.openapi.util.TextRange;
import com.intellij.psi.PsiElement;
import com.intellij.psi.PsiFile;
import com.intellij.psi.PsiReference;
import com.intellij.util.ArrayUtil;
import com.intellij.util.containers.ContainerUtil;
import org.intellij.erlang.psi.ErlangFile;
import org.intellij.erlang.psi.ErlangModuleRef;
import org.intellij.erlang.psi.ErlangQAtom;
import org.intellij.erlang.psi.ErlangTypeDefinition;
import org.jetbrains.annotations.NotNull;
import org.jetbrains.annotations.Nullable;

/**
 * @author ignatov
 */
public class ErlangTypeReferenceImpl<T extends ErlangQAtom> extends ErlangAtomBasedReferenceImpl<T> {
  @Nullable
  private final ErlangModuleRef myModuleRef;

  public ErlangTypeReferenceImpl(@NotNull T element, @Nullable ErlangModuleRef moduleRef, TextRange range, String name) {
    super(element, range, name);
    myModuleRef = moduleRef;
  }

  @Override
  public PsiElement resolve() {
    PsiFile containingFile = getPsiFile();
    if (containingFile instanceof ErlangFile) {
      ErlangTypeDefinition type = ((ErlangFile) containingFile).getType(myReferenceName);
      if (type != null) return type;
      return ContainerUtil.getFirstItem(ErlangPsiImplUtil.getErlangTypeFromIncludes((ErlangFile) containingFile, false, myReferenceName));
    }
    return null;
  }

  @NotNull
  private PsiFile getPsiFile() {
    PsiReference reference = myModuleRef != null ? myModuleRef.getReference() : null;
    PsiElement resolve = reference != null ? reference.resolve() : null;
    PsiFile moduleRefContainingFile = resolve != null ? resolve.getContainingFile() : null;
    return moduleRefContainingFile != null ? moduleRefContainingFile : myElement.getContainingFile();
  }

  @NotNull
  @Override
  public Object[] getVariants() {
    return ArrayUtil.toObjectArray(ErlangPsiImplUtil.getTypeLookupElements(getPsiFile()));
  }
}
