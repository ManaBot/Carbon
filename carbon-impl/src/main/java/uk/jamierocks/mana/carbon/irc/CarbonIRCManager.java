/*
 * Copyright 2016 Jamie Mansfield
 *
 * Licensed under the Apache License, Version 2.0 (the "License");
 * you may not use this file except in compliance with the License.
 * You may obtain a copy of the License at
 *
 *     http://www.apache.org/licenses/LICENSE-2.0
 *
 * Unless required by applicable law or agreed to in writing, software
 * distributed under the License is distributed on an "AS IS" BASIS,
 * WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied.
 * See the License for the specific language governing permissions and
 * limitations under the License.
 */

package uk.jamierocks.mana.carbon.irc;

import static com.google.common.base.Preconditions.checkNotNull;
import static uk.jamierocks.mana.carbon.Carbon.getCarbon;
import static uk.jamierocks.mana.carbon.util.Constants.OPS_PATH;

import com.google.common.collect.Lists;
import com.google.common.collect.Maps;
import com.google.common.reflect.TypeToken;
import ninja.leaping.configurate.ConfigurationNode;
import ninja.leaping.configurate.gson.GsonConfigurationLoader;
import ninja.leaping.configurate.objectmapping.ObjectMappingException;
import org.kitteh.irc.client.library.Client;
import org.kitteh.irc.client.library.util.AcceptingTrustManagerFactory;
import uk.jamierocks.mana.carbon.CarbonImpl;
import uk.jamierocks.mana.carbon.service.exception.ExceptionReporter;

import java.io.IOException;
import java.util.Collection;
import java.util.Collections;
import java.util.List;
import java.util.Map;
import java.util.Optional;

/**
 * The implementation of {@link IRCManager} for Carbon.
 *
 * @author Jamie Mansfield
 * @since 1.0.0
 */
public final class CarbonIRCManager implements IRCManager {

    private final Map<String, Client> clients = Maps.newHashMap();

    public void initialise() {
        ConfigurationNode configurationNode = getCarbon().getConfiguration().getNode().getNode("irc");
        for (ConfigurationNode network : configurationNode.getNode("networks").getChildrenList()) {
            Client.Builder clientBuilder = Client.builder()
                    .secureTrustManagerFactory(new AcceptingTrustManagerFactory())
                    .name(network.getNode("id").getString())
                    .serverHost(network.getNode("host").getString())
                    .serverPort(network.getNode("port").getInt())
                    .secure(network.getNode("secure").getBoolean())
                    .user(network.getNode("username").getString())
                    .nick(network.getNode("nickname").getString())
                    .listenOutput(CarbonImpl.LOGGER::debug)
                    .listenException(e -> ExceptionReporter
                            .report("KittehIRCClientLibrary has experienced an exception!", e));
            if (!network.getNode("serverPassword").isVirtual()) {
                clientBuilder.serverPassword(network.getNode("serverPassword").getString());
            }
            this.clients.put(network.getNode("id").getString(), clientBuilder.build());
        }
    }

    /**
     * {@inheritDoc}
     */
    @Override
    public Optional<Client> getClient(String id) {
        checkNotNull(id, "id is null!");
        return Optional.ofNullable(this.clients.get(id));
    }

    /**
     * {@inheritDoc}
     */
    @Override
    public Collection<Client> getClients() {
        return Collections.unmodifiableCollection(this.clients.values());
    }

    /**
     * {@inheritDoc}
     */
    @Override
    public List<String> getAdministrators() {
        try {
            final ConfigurationNode node = GsonConfigurationLoader.builder().setPath(OPS_PATH).build().load();
            return node.getList(TypeToken.of(String.class));
        } catch (IOException | ObjectMappingException e) {
            ExceptionReporter.report("Failed to get bot administrators!", e);
            return Lists.newArrayList();
        }
    }
}
