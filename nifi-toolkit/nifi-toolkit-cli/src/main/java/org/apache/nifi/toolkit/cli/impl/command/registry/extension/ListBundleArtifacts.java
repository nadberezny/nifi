/*
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
package org.apache.nifi.toolkit.cli.impl.command.registry.extension;

import org.apache.commons.cli.ParseException;
import org.apache.nifi.registry.client.ExtensionRepoClient;
import org.apache.nifi.registry.client.NiFiRegistryClient;
import org.apache.nifi.registry.client.NiFiRegistryException;
import org.apache.nifi.registry.extension.repo.ExtensionRepoArtifact;
import org.apache.nifi.toolkit.cli.api.Context;
import org.apache.nifi.toolkit.cli.impl.command.CommandOption;
import org.apache.nifi.toolkit.cli.impl.command.registry.AbstractNiFiRegistryCommand;
import org.apache.nifi.toolkit.cli.impl.result.registry.ExtensionRepoArtifactsResult;

import java.io.IOException;
import java.util.List;
import java.util.Properties;

public class ListBundleArtifacts extends AbstractNiFiRegistryCommand<ExtensionRepoArtifactsResult> {

    public ListBundleArtifacts() {
        super("list-bundle-artifacts", ExtensionRepoArtifactsResult.class);
    }

    @Override
    protected void doInitialize(final Context context) {
        addOption(CommandOption.BUCKET_NAME.createOption());
        addOption(CommandOption.EXT_BUNDLE_GROUP.createOption());
    }

    @Override
    public String getDescription() {
        return "List the bundle artifacts in the given bucket and group.";
    }

    @Override
    public ExtensionRepoArtifactsResult doExecute(final NiFiRegistryClient client, final Properties properties)
            throws IOException, NiFiRegistryException, ParseException {

        final String bucketName = getRequiredArg(properties, CommandOption.BUCKET_NAME);
        final String groupId = getRequiredArg(properties, CommandOption.EXT_BUNDLE_GROUP);

        final ExtensionRepoClient extensionRepoClient = client.getExtensionRepoClient();
        final List<ExtensionRepoArtifact> artifacts = extensionRepoClient.getArtifacts(bucketName, groupId);

        return new ExtensionRepoArtifactsResult(getResultType(properties), artifacts);
    }
}
