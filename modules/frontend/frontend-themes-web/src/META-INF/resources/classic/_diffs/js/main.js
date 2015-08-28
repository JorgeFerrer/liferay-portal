AUI().ready(
	'liferay-sign-in-modal',
	function(A) {
		var signIn = A.one('li.sign-in a');

		if (signIn && signIn.getData('redirect') !== 'true') {
			signIn.plug(Liferay.SignInModal);
		}
	}
);