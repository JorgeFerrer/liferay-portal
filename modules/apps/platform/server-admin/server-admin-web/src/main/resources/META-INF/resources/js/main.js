AUI.add(
	'liferay-admin',
	function(A) {
		var Lang = A.Lang;

		var MAP_DATA_PARAMS = {
			classname: 'className'
		};

		var RENDER_INTERVAL_IDLE = 60000;

		var RENDER_INTERVAL_IN_PROGRESS = 2000;

		var STR_CLICK = 'click';

		var STR_FORM = 'form';

		var STR_INDEX_ACTIONS_PANEL = 'indexActionsPanel';

		var STR_URL = 'url';

		var Admin = A.Component.create(
			{
				ATTRS: {
					form: {
						setter: A.one,
						value: null
					},

					indexActionsPanel: {
						value: null
					},

					redirectUrl: {
						validator: Lang.isString,
						value: null
					},

					submitButton: {
						validator: Lang.isString,
						value: null
					},

					url: {
						value: null
					}
				},

				AUGMENTS: [Liferay.PortletBase],

				EXTENDS: A.Base,

				NAME: 'admin',

				prototype: {
					initializer: function(config) {
						var instance = this;

						instance._eventHandles = [];

						instance.bindUI();

						instance._laterTimeout = A.later(RENDER_INTERVAL_IN_PROGRESS, instance, instance._updateIndexActions);
					},

					bindUI: function() {
						var instance = this;

						instance._eventHandles.push(
							instance.get(STR_FORM).delegate(
								STR_CLICK,
								A.bind('_onSubmit', instance),
								instance.get('submitButton')
							)
						);
					},

					destructor: function() {
						var instance = this;

						A.Array.invoke(instance._eventHandles, 'detach');

						instance._eventHandles = null;

						A.clearTimeout(instance._laterTimeout);
					},

					_addInputsFromData: function(data) {
						var instance = this;

						var form = instance.get(STR_FORM);

						var inputsArray = A.Object.map(
							data,
							function(value, key) {
								key = MAP_DATA_PARAMS[key] || key;

								var nsKey = instance.ns(key);

								return '<input id="' + nsKey + '" name="' + nsKey + '" type="hidden" value="' + value + '" />';
							}
						);

						form.append(inputsArray.join(''));
					},

					_isBackgroundTaskInProgress: function() {
						var instance = this;

						var indexActionsNode = A.one(instance.get(STR_INDEX_ACTIONS_PANEL));

						return !!indexActionsNode.one('.background-task-status-in-progress');
					},

					_installXuggler: function(event) {
						var instance = this;

						var form = instance.get(STR_FORM);

						A.one('#adminXugglerPanelContent').load(
							instance.get(STR_URL),
							{
								form: form.getDOM(),
								loadingMask: {
									'strings.loading': Liferay.Language.get('xuggler-library-is-installing')
								},
								selector: '#adminXugglerPanelContent',
								where: 'outer'
							}
						);
					},

					_onSubmit: function(event) {
						var instance = this;

						var data = event.currentTarget.getData();
						var form = instance.get(STR_FORM);

						var cmd = data.cmd;
						var redirect = instance.one('#redirect', form);

						if (redirect) {
							redirect.val(instance.get('redirectURL'));
						}

						instance._addInputsFromData(data);

						if (cmd === 'installXuggler') {
							var cmdNode = instance.one('#cmd');

							instance._installXuggler();

							if (cmdNode) {
								cmdNode.remove();
							}

							instance._installXuggler();
						}
						else {
							submitForm(
								form,
								instance.get(STR_URL)
							);
						}
					},

					_updateIndexActions: function() {
						var instance = this;

						var renderInterval = RENDER_INTERVAL_IDLE;

						if (instance._isBackgroundTaskInProgress()) {
							renderInterval = RENDER_INTERVAL_IN_PROGRESS;
						}

						A.io.request(
							instance.get(STR_URL),
							{
								on: {
									success: function(event, id, obj) {
										var responseDataNode = A.Node.create(this.get('responseData'));

										var responseAdminIndexPanel = responseDataNode.one(instance.get(STR_INDEX_ACTIONS_PANEL));
										var responseAdminIndexNodeList = responseAdminIndexPanel.all('.index-action-wrapper');

										var currentAdminIndexPanel = A.one(instance.get(STR_INDEX_ACTIONS_PANEL));
										var currentAdminIndexNodeList = currentAdminIndexPanel.all('.index-action-wrapper');

										currentAdminIndexNodeList.each(function(node, index) {
											var currentIsInProgress = !!node.one('.progress');

											var responseAdminIndexNode = responseAdminIndexNodeList.item(index); 
											var responseIsInProgress = !!responseAdminIndexNode.one('.progress');

											if (currentIsInProgress || responseIsInProgress) {
												node.replace(responseAdminIndexNode);
											}
										});
									}
								}
							}
						);

						instance._laterTimeout = A.later(renderInterval, instance, instance._updateIndexActions);
					}
				}
			}
		);

		Liferay.Portlet.Admin = Admin;
	},
	'',
	{
		requires: ['aui-io-plugin-deprecated', 'aui-io-request', 'liferay-portlet-base']
	}
);