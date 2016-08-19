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

package uk.jamierocks.mana.carbon.modules.help;

import com.google.common.eventbus.Subscribe;
import uk.jamierocks.mana.carbon.event.state.PostInitialisationEvent;
import uk.jamierocks.mana.carbon.module.Module;
import uk.jamierocks.mana.carbon.modules.help.command.HelpCommand;

/**
 * A help module for Carbon.
 *
 * @author Jamie Mansfield
 * @since 1.1.0
 */
@Module(id = "help", name = "Help")
public final class HelpModule {

    @Subscribe
    public void onPostInitialisation(PostInitialisationEvent event) {
        // Register commands
        event.getCarbon().getCommandDispatcher().registerCommand(new HelpCommand(), "help");
    }
}
