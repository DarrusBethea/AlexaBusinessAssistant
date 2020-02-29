"use strict";
Object.defineProperty(exports, "__esModule", { value: true });
const EventEmitter = require("events");
const _cloneDeep = require("lodash.clonedeep");
const _get = require("lodash.get");
const _isEqual = require("lodash.isequal");
const _merge = require("lodash.merge");
const _transform = require("lodash.transform");
const ActionSet_1 = require("./ActionSet");
const Log_1 = require("./Log");
/**
 * Allows a class to use plugins
 */
class Extensible extends EventEmitter.EventEmitter {
    constructor(config) {
        super();
        this.config = {
            enabled: true,
            plugin: {},
        };
        this.$plugins = new Map();
        this.setMaxListeners(0);
        if (config) {
            this.config = _merge(this.config, config);
        }
        this.actionSet = new ActionSet_1.ActionSet([], this);
    }
    /**
     * Adds plugin to base class
     * @param {Plugin} plugins
     * @returns {this}
     */
    use(...plugins) {
        for (const plugin of plugins) {
            const name = plugin.name || plugin.constructor.name;
            if (plugin.config) {
                const constructor = plugin.constructor;
                const emptyPluginObject = new constructor();
                const pluginDefaultConfig = _cloneDeep(emptyPluginObject.config);
                if (typeof pluginDefaultConfig.enabled === 'undefined') {
                    pluginDefaultConfig.enabled = true;
                }
                if (typeof plugin.config.enabled === 'undefined') {
                    plugin.config.enabled = true;
                }
                const appConfig = _cloneDeep(this.config);
                const constructorConfig = difference(plugin.config, pluginDefaultConfig);
                for (const prop in plugin.config) {
                    if (plugin.config.hasOwnProperty(prop) && pluginDefaultConfig.hasOwnProperty(prop)) {
                        let val;
                        const appConfigVal = _get(appConfig, `plugin.${name}.${prop}`);
                        if (typeof constructorConfig[prop] !== 'undefined') {
                            val = plugin.config[prop];
                        }
                        else if (typeof appConfigVal !== 'undefined') {
                            val = appConfigVal;
                        }
                        else {
                            val = pluginDefaultConfig[prop];
                        }
                        if (typeof val === 'object') {
                            if (Array.isArray(val)) {
                                plugin.config[prop] = !plugin.config[prop]
                                    ? [...val]
                                    : [...plugin.config[prop], ...val];
                            }
                            else {
                                plugin.config[prop] = !plugin.config[prop]
                                    ? val
                                    : Object.assign(Object.assign({}, plugin.config[prop]), val);
                            }
                        }
                        else {
                            plugin.config[prop] = val;
                        }
                    }
                    else if (prop !== 'plugin') {
                        Log_1.Log.verbose(`[${name}] Property '${prop}' passed as config-option for plugin '${name}' but not defined in the default-config. Only properties that exist in the default-configuration can be set!`);
                    }
                }
                if (!this.config.plugin) {
                    this.config.plugin = {};
                }
                this.config.plugin[name] = plugin.config;
            }
            // remove existing plugin with the same name
            if (this.$plugins.get(name)) {
                const existingPlugin = this.$plugins.get(name);
                if (existingPlugin && existingPlugin.uninstall) {
                    existingPlugin.uninstall(this);
                }
            }
            const isPluginEnabled = typeof plugin.config === 'undefined' ||
                (plugin.config && typeof plugin.config.enabled === 'undefined') ||
                (plugin.config && plugin.config.enabled === true);
            if (isPluginEnabled) {
                // enabled with config, and boolean enabled property
                this.$plugins.set(name, plugin);
                plugin.install(this);
                if (this.constructor.name === 'App') {
                    Log_1.Log.yellow().verbose(`Installed plugin: ${name} (${this.constructor.name})`);
                    Log_1.Log.debug(`${JSON.stringify(plugin.config || {}, null, '\t')}`);
                    Log_1.Log.debug();
                }
                this.emit('use', plugin);
            }
        }
        return this;
    }
    /**
     * Removes all plugins from extensible object
     * Calls plugin's uninstall() method
     */
    removeAll() {
        this.$plugins.forEach((entry) => {
            if (entry && entry.uninstall) {
                entry.uninstall(this);
            }
        });
        this.$plugins.clear();
    }
    /**
     * Removes plugin from plugins
     * @param {string} name
     */
    remove(name) {
        const plugin = this.$plugins.get(name);
        if (plugin) {
            if (plugin.uninstall) {
                plugin.uninstall(this);
            }
            this.$plugins.delete(name);
            Log_1.Log.verbose(`Removed plugin: ${name}`);
        }
    }
    /**
     * Return or initialize middleware
     * @param {string} name
     * @returns {Middleware}
     */
    middleware(name) {
        if (!this.actionSet.get(name)) {
            return this.actionSet.create(name, this);
        }
        return this.actionSet.get(name);
    }
    /**
     * Check for middleware with given name
     * @param {string} name
     * @return {boolean}
     */
    hasMiddleware(name) {
        return typeof this.actionSet.get(name) !== 'undefined';
    }
}
exports.Extensible = Extensible;
/**
 * @see https://gist.github.com/Yimiprod/7ee176597fef230d1451
 * Deep diff between two object, using lodash
 * @param  {Record<string, any>} aObject Object compared
 * @param  {Record<string, any>} aBase   Object to compare with
 * @return {Record<string, any>>}        Return a new object who represent the diff
 */
// tslint:disable:no-any
function difference(aObject, aBase) {
    function changes(object, base) {
        return _transform(object, (result, value, key) => {
            if (!_isEqual(value, base[key])) {
                result[key] =
                    typeof value === 'object' && typeof base[key] === 'object'
                        ? changes(value, base[key])
                        : value;
            }
        });
    }
    return changes(aObject, aBase);
}
// tslint:enable:no-any
//# sourceMappingURL=Extensible.js.map