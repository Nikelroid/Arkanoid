/**
 * ADOBE CONFIDENTIAL
 *  _________________
 *  Copyright 2016 Adobe Systems Incorporated
 *  All Rights Reserved.
 *
 * NOTICE:  All information contained herein is, and remains
 * the property of Adobe Systems Incorporated and its suppliers,
 * if any.  The intellectual and technical concepts contained
 * herein are proprietary to Adobe Systems Incorporated and its
 * suppliers and are protected by all applicable intellectual property
 * laws, including trade secret and copyright laws.
 * Dissemination of this information or reproduction of this material
 * is strictly forbidden unless prior written permission is obtained
 * from Adobe Systems Incorporated.
 */
/*************************************************************************
 * ADOBE CONFIDENTIAL
 * ___________________
 *
 *  Copyright 2015 Adobe Systems Incorporated
 *  All Rights Reserved.
 *
 * NOTICE:  All information contained herein is, and remains
 * the property of Adobe Systems Incorporated and its suppliers,
 * if any.  The intellectual and technical concepts contained
 * herein are proprietary to Adobe Systems Incorporated and its
 * suppliers and are protected by all applicable intellectual property
 * laws, including trade secret and copyright laws.
 * Dissemination of this information or reproduction of this material
 * is strictly forbidden unless prior written permission is obtained
 * from Adobe Systems Incorporated.
 **************************************************************************/

/*
	Service helper that holds the objects wanting to run something when the app starts.

	The functions you should have attached to your objects correspond to PHASES.
	eg if you want to listen for when everything has been loaded, make sure you have a function called "whenReady".

	Example usage:
	var startupObj = {
		whenHostReady: function() {
			// your code here
		}
	};

	iaw.startup.add(startupObj);
*/
var iaw = iaw || {};
iaw.startup = {
	PHASES: {
		Doc: 'whenDocReady', // after the document finishes loading, before host data is loaded
		Host: 'whenHostReady', // after host data has been loaded
		Done: 'whenReady' // after everything has been loaded
	},

	_objs: [],

	/*
		Pass in the object that holds the (public) function(s) to be called during the startup sequence.
	*/
	add: function(startupObj) {
		var haveCB = false;
		for (var p in this.PHASES) {
			if (startupObj[this.PHASES[p]]) {
				haveCB = true;
				break;
			}
		}
		if (!haveCB) {
			throw new Error('[iaw.init] Add at least one init callback before adding to startup sequence.');
		}
		this._objs.push(startupObj);
	},

	remove: function(startupObj) {
		var i = this._objs.indexOf(startupObj);
		if (i === -1) return;
		this._objs.splice(i, 1);
		console.log(this._objs);
	},

	/*
		Cycles through all objects to call their functions if they exist. No arguments are passed.
		@private
	*/
	run: function(phase) {
		var i, o;
		// console.log('[Startup] Running '+phase);
		for (i = 0; i < this._objs.length; i++) {
			o = this._objs[i];
			if (o[phase]) {
				o[phase].call(o);
			}
		}
	}
};

//===================================================================================
//
//  ADOBE CONFIDENTIAL
//
//  Copyright © 2015 Adobe Systems Incorporated
//  All Rights Reserved.
//
//  NOTICE:  All information contained herein is, and remains
//  the property of Adobe Systems Incorporated and its suppliers,
//  if any.  The intellectual and technical concepts contained
//  herein are proprietary to Adobe Systems Incorporated and its
//  suppliers and are protected by all applicable intellectual property
//  laws, including trade secret and copyright laws.
//
//  Dissemination of this information or reproduction of this material
//  is strictly forbidden unless prior written permission is obtained
//  from Adobe Systems Incorporated.
//
//===================================================================================

/**
 * Simple singleton style utility container so accessibility related
 * stuff has a centralized wrapper.
 */
var iaw = iaw || {};

iaw.a11y = function() {
	var KEYS_OF_INTEREST_MAC = [
		{
			/* Enter */
			keyCode: 36
		},
		{
			/* Tab */
			keyCode: 48
		},
		{
			/* Shift+Tab */
			keyCode: 48,
			shiftKey: true
		},
		{
			/* Space */
			keyCode: 49
		},
		{
			/* Esc */
			keyCode: 53
		},
		{
			/* Home */
			keyCode: 115
		},
		{
			/* Page Up */
			keyCode: 116
		},
		{
			/* End */
			keyCode: 119
		},
		{
			/* Page Down */
			keyCode: 121
		},
		{
			/* Left Arrow */
			keyCode: 123
		},
		{
			/* Right Arrow */
			keyCode: 124
		},
		{
			/* Down Arrow */
			keyCode: 125
		},
		{
			/* Up Arrow */
			keyCode: 126
		},
		{
			/* A */
			keyCode: 0
		},
		{
			/* B */
			keyCode: 11
		},
		{
			/* C */
			keyCode: 8
		},
		{
			/* D */
			keyCode: 2
		},
		{
			/* E */
			keyCode: 14
		},
		{
			/* F */
			keyCode: 3
		},
		{
			/* G */
			keyCode: 5
		},
		{
			/* H */
			keyCode: 4
		},
		{
			/* I */
			keyCode: 34
		},
		{
			/* J */
			keyCode: 38
		},
		{
			/* K */
			keyCode: 40
		},
		{
			/* L */
			keyCode: 37
		},
		{
			/* M */
			keyCode: 46
		},
		{
			/* N */
			keyCode: 45
		},
		{
			/* O */
			keyCode: 31
		},
		{
			/* P */
			keyCode: 35
		},
		{
			/* Q */
			keyCode: 12
		},
		{
			/* R */
			keyCode: 15
		},
		{
			/* S */
			keyCode: 1
		},
		{
			/* T */
			keyCode: 17
		},
		{
			/* U */
			keyCode: 32
		},
		{
			/* V */
			keyCode: 9
		},
		{
			/* W */
			keyCode: 13
		},
		{
			/* X */
			keyCode: 7
		},
		{
			/* Y */
			keyCode: 16
		},
		{
			/* Z */
			keyCode: 6
		},
		{
			/* ` */
			keyCode: 50
		},
		{
			/* 1 */
			keyCode: 18
		},
		{
			/* 2 */
			keyCode: 19
		},
		{
			/* 3 */
			keyCode: 20
		},
		{
			/* 4 */
			keyCode: 21
		},
		{
			/* 5 */
			keyCode: 23
		},
		{
			/* 6 */
			keyCode: 22
		},
		{
			/* 7 */
			keyCode: 26
		},
		{
			/* 8 */
			keyCode: 28
		},
		{
			/* 9 */
			keyCode: 25
		},
		{
			/* 0 */
			keyCode: 29
		},
		{
			/* - */
			keyCode: 27
		},
		{
			/* = */
			keyCode: 24
		},
		{
			/* [ */
			keyCode: 33
		},
		{
			/* ] */
			keyCode: 30
		},
		{
			/* \ */
			keyCode: 42
		},
		{
			/* ; */
			keyCode: 41
		},
		{
			/* ' */
			keyCode: 39
		},
		{
			/* , */
			keyCode: 43
		},
		{
			/* . */
			keyCode: 47
		},
		{
			/* / */
			keyCode: 44
		},
		{
			/* A */
			keyCode: 0,
			shiftKey: true
		},
		{
			/* B */
			keyCode: 11,
			shiftKey: true
		},
		{
			/* C */
			keyCode: 8,
			shiftKey: true
		},
		{
			/* D */
			keyCode: 2,
			shiftKey: true
		},
		{
			/* E */
			keyCode: 14,
			shiftKey: true
		},
		{
			/* F */
			keyCode: 3,
			shiftKey: true
		},
		{
			/* G */
			keyCode: 5,
			shiftKey: true
		},
		{
			/* H */
			keyCode: 4,
			shiftKey: true
		},
		{
			/* I */
			keyCode: 34,
			shiftKey: true
		},
		{
			/* J */
			keyCode: 38,
			shiftKey: true
		},
		{
			/* K */
			keyCode: 40,
			shiftKey: true
		},
		{
			/* L */
			keyCode: 37,
			shiftKey: true
		},
		{
			/* M */
			keyCode: 46,
			shiftKey: true
		},
		{
			/* N */
			keyCode: 45,
			shiftKey: true
		},
		{
			/* O */
			keyCode: 31,
			shiftKey: true
		},
		{
			/* P */
			keyCode: 35,
			shiftKey: true
		},
		{
			/* Q */
			keyCode: 12,
			shiftKey: true
		},
		{
			/* R */
			keyCode: 15,
			shiftKey: true
		},
		{
			/* S */
			keyCode: 1,
			shiftKey: true
		},
		{
			/* T */
			keyCode: 17,
			shiftKey: true
		},
		{
			/* U */
			keyCode: 32,
			shiftKey: true
		},
		{
			/* V */
			keyCode: 9,
			shiftKey: true
		},
		{
			/* W */
			keyCode: 13,
			shiftKey: true
		},
		{
			/* X */
			keyCode: 7,
			shiftKey: true
		},
		{
			/* Y */
			keyCode: 16,
			shiftKey: true
		},
		{
			/* Z */
			keyCode: 6,
			shiftKey: true
		},
		{
			/* ` */
			keyCode: 50,
			shiftKey: true
		},
		{
			/* 1 */
			keyCode: 18,
			shiftKey: true
		},
		{
			/* 2 */
			keyCode: 19,
			shiftKey: true
		},
		{
			/* 3 */
			keyCode: 20,
			shiftKey: true
		},
		{
			/* 4 */
			keyCode: 21,
			shiftKey: true
		},
		{
			/* 5 */
			keyCode: 23,
			shiftKey: true
		},
		{
			/* 6 */
			keyCode: 22,
			shiftKey: true
		},
		{
			/* 7 */
			keyCode: 26,
			shiftKey: true
		},
		{
			/* 8 */
			keyCode: 28,
			shiftKey: true
		},
		{
			/* 9 */
			keyCode: 25,
			shiftKey: true
		},
		{
			/* 0 */
			keyCode: 29,
			shiftKey: true
		},
		{
			/* - */
			keyCode: 27,
			shiftKey: true
		},
		{
			/* = */
			keyCode: 24,
			shiftKey: true
		},
		{
			/* [ */
			keyCode: 33,
			shiftKey: true
		},
		{
			/* ] */
			keyCode: 30,
			shiftKey: true
		},
		{
			/* \ */
			keyCode: 42,
			shiftKey: true
		},
		{
			/* ; */
			keyCode: 41,
			shiftKey: true
		},
		{
			/* ' */
			keyCode: 39,
			shiftKey: true
		},
		{
			/* , */
			keyCode: 43,
			shiftKey: true
		},
		{
			/* . */
			keyCode: 47,
			shiftKey: true
		},
		{
			/* / */
			keyCode: 44,
			shiftKey: true
		},
		{
			/* / */
			keyCode: 75
		},
		{
			/* * */
			keyCode: 67
		},
		{
			/* - */
			keyCode: 78
		},
		{
			/* + */
			keyCode: 69
		},
		{
			/* ⌫ */
			keyCode: 51
		}
	];

	var KEYS_OF_INTEREST_WIN = [
		{
			/* Enter */
			keyCode: 0x0D
		},
		{
			/* Tab */
			keyCode: 0x09
		},
		{
			/* Shift+Tab */
			keyCode: 0x09,
			shiftKey: true
		},
		{
			/* Space */
			keyCode: 0x20
		},
		{
			/* Esc */
			keyCode: 0x1B
		},
		{
			/* Home */
			keyCode: 0x24
		},
		{
			/* Page Up */
			keyCode: 0x21
		},
		{
			/* End */
			keyCode: 0x23
		},
		{
			/* Page Down */
			keyCode: 0x22
		},
		{
			/* Left Arrow */
			keyCode: 0x25
		},
		{
			/* Right Arrow */
			keyCode: 0x27
		},
		{
			/* Down Arrow */
			keyCode: 0x28
		},
		{
			/* Up Arrow */
			keyCode: 0x26
		},
		{
			/* A */
			keyCode: 0x41
		},
		{
			/* B */
			keyCode: 0x42
		},
		{
			/* C */
			keyCode: 0x43
		},
		{
			/* D */
			keyCode: 0x44
		},
		{
			/* E */
			keyCode: 0x45
		},
		{
			/* F */
			keyCode: 0x46
		},
		{
			/* G */
			keyCode: 0x47
		},
		{
			/* H */
			keyCode: 0x48
		},
		{
			/* I */
			keyCode: 0x49
		},
		{
			/* J */
			keyCode: 0x4A
		},
		{
			/* K */
			keyCode: 0x4B
		},
		{
			/* L */
			keyCode: 0x4C
		},
		{
			/* M */
			keyCode: 0x4D
		},
		{
			/* N */
			keyCode: 0x4E
		},
		{
			/* O */
			keyCode: 0x4F
		},
		{
			/* P */
			keyCode: 0x50
		},
		{
			/* Q */
			keyCode: 0x51
		},
		{
			/* R */
			keyCode: 0x52
		},
		{
			/* S */
			keyCode: 0x53
		},
		{
			/* T */
			keyCode: 0x54
		},
		{
			/* U */
			keyCode: 0x55
		},
		{
			/* V */
			keyCode: 0x56
		},
		{
			/* W */
			keyCode: 0x57
		},
		{
			/* X */
			keyCode: 0x58
		},
		{
			/* Y */
			keyCode: 0x59
		},
		{
			/* Z */
			keyCode: 0x5A
		},
		{
			/* ` */
			keyCode: 0xC0
		},
		{
			/* 1 */
			keyCode: 0x31
		},
		{
			/* 2 */
			keyCode: 0x32
		},
		{
			/* 3 */
			keyCode: 0x33
		},
		{
			/* 4 */
			keyCode: 0x34
		},
		{
			/* 5 */
			keyCode: 0x35
		},
		{
			/* 6 */
			keyCode: 0x36
		},
		{
			/* 7 */
			keyCode: 0x37
		},
		{
			/* 8 */
			keyCode: 0x38
		},
		{
			/* 9 */
			keyCode: 0x39
		},
		{
			/* 0 */
			keyCode: 0x30
		},
		{
			/* - */
			keyCode: 0xBD
		},
		{
			/* = */
			keyCode: 0xBB
		},
		{
			/* [ */
			keyCode: 0xDB
		},
		{
			/* ] */
			keyCode: 0xDD
		},
		{
			/* \ */
			keyCode: 0xDC
		},
		{
			/* ; */
			keyCode: 0xBA
		},
		{
			/* ' */
			keyCode: 0xDE
		},
		{
			/* , */
			keyCode: 0xBC
		},
		{
			/* . */
			keyCode: 0xBE
		},
		{
			/* / */
			keyCode: 0xBF
		},
		{
			/* A */
			keyCode: 0x41,
			shiftKey: true
		},
		{
			/* B */
			keyCode: 0x42,
			shiftKey: true
		},
		{
			/* C */
			keyCode: 0x43,
			shiftKey: true
		},
		{
			/* D */
			keyCode: 0x44,
			shiftKey: true
		},
		{
			/* E */
			keyCode: 0x45,
			shiftKey: true
		},
		{
			/* F */
			keyCode: 0x46,
			shiftKey: true
		},
		{
			/* G */
			keyCode: 0x47,
			shiftKey: true
		},
		{
			/* H */
			keyCode: 0x48,
			shiftKey: true
		},
		{
			/* I */
			keyCode: 0x49,
			shiftKey: true
		},
		{
			/* J */
			keyCode: 0x4A,
			shiftKey: true
		},
		{
			/* K */
			keyCode: 0x4B,
			shiftKey: true
		},
		{
			/* L */
			keyCode: 0x4C,
			shiftKey: true
		},
		{
			/* M */
			keyCode: 0x4D,
			shiftKey: true
		},
		{
			/* N */
			keyCode: 0x4E,
			shiftKey: true
		},
		{
			/* O */
			keyCode: 0x4F,
			shiftKey: true
		},
		{
			/* P */
			keyCode: 0x50,
			shiftKey: true
		},
		{
			/* Q */
			keyCode: 0x51,
			shiftKey: true
		},
		{
			/* R */
			keyCode: 0x52,
			shiftKey: true
		},
		{
			/* S */
			keyCode: 0x53,
			shiftKey: true
		},
		{
			/* T */
			keyCode: 0x54,
			shiftKey: true
		},
		{
			/* U */
			keyCode: 0x55,
			shiftKey: true
		},
		{
			/* V */
			keyCode: 0x56,
			shiftKey: true
		},
		{
			/* W */
			keyCode: 0x57,
			shiftKey: true
		},
		{
			/* X */
			keyCode: 0x58,
			shiftKey: true
		},
		{
			/* Y */
			keyCode: 0x59,
			shiftKey: true
		},
		{
			/* Z */
			keyCode: 0x5A,
			shiftKey: true
		},
		{
			/* ` */
			keyCode: 0xC0,
			shiftKey: true
		},
		{
			/* 1 */
			keyCode: 0x31,
			shiftKey: true
		},
		{
			/* 2 */
			keyCode: 0x32,
			shiftKey: true
		},
		{
			/* 3 */
			keyCode: 0x33,
			shiftKey: true
		},
		{
			/* 4 */
			keyCode: 0x34,
			shiftKey: true
		},
		{
			/* 5 */
			keyCode: 0x35,
			shiftKey: true
		},
		{
			/* 6 */
			keyCode: 0x36,
			shiftKey: true
		},
		{
			/* 7 */
			keyCode: 0x37,
			shiftKey: true
		},
		{
			/* 8 */
			keyCode: 0x38,
			shiftKey: true
		},
		{
			/* 9 */
			keyCode: 0x39,
			shiftKey: true
		},
		{
			/* 0 */
			keyCode: 0x30,
			shiftKey: true
		},
		{
			/* - */
			keyCode: 0xBD,
			shiftKey: true
		},
		{
			/* = */
			keyCode: 0xBB,
			shiftKey: true
		},
		{
			/* [ */
			keyCode: 0xDB,
			shiftKey: true
		},
		{
			/* ] */
			keyCode: 0xDD,
			shiftKey: true
		},
		{
			/* \ */
			keyCode: 0xDC,
			shiftKey: true
		},
		{
			/* ; */
			keyCode: 0xBA,
			shiftKey: true
		},
		{
			/* ' */
			keyCode: 0xDE,
			shiftKey: true
		},
		{
			/* , */
			keyCode: 0xBC,
			shiftKey: true
		},
		{
			/* . */
			keyCode: 0xBE,
			shiftKey: true
		},
		{
			/* / */
			keyCode: 0xBF,
			shiftKey: true
		},
		{
			/* / */
			keyCode: 0x6F
		},
		{
			/* * */
			keyCode: 0x6A
		},
		{
			/* - */
			keyCode: 0x6D
		},
		{
			/* + */
			keyCode: 0x6B
		},
		{
			/* ⌫ */
			keyCode: 0x08
		}
	];

	var FOCUSABLE_SELECTOR = ['input:not([type="hidden"]):not(:disabled)', ' select:not(:disabled)', 'a[href]', 'textarea:not(:disabled)', 'button:not(:disabled)', '[tabindex]'].join(',');

	// variables for alphanumeric seach
	var searchString = '';
	var searchStringDelay = 800;
	var searchTimeout;
	var whitespaceRegex = /\s+/g;
	var regexRegex = /[\-\[\]{}()*+?.,\\\^$|#\s]/g;

	// Utility method to evaluate whether an node or any of its parents is hidden.
	var nodeCache = {}, cacheIndex = 1;
	function isHidden(node) {
		if (node === document.documentElement) {
			return false;
		}

		// No need to test node if we already have tested its display/visibility.
		if (node.focusableCacheIndex) {
			return nodeCache[node.focusableCacheIndex];
		}

		var result = false,
			style = window.getComputedStyle(node);
		if (style.visibility === 'hidden' || style.display === 'none') {
			result = true;
		}
		else if (node.parentNode) {
			result = isHidden(node.parentNode);
		}

		// Once node has been tested store result.
		node.focusableCacheIndex = cacheIndex;
		nodeCache[node.focusableCacheIndex] = result;
		cacheIndex++;

		return result;
	}

	function onLoadEvent() {
		console.log(); // this is a hack to force this function to run; don't remove
		var hadKeyboardEvent = false,
			keyboardModalityWhitelist = ['input:not([type])', 'input[type=text]', 'input[type=checkbox]', 'input[type=radio]', 'input[type=number]', 'input[type=date]', 'input[type=time]', 'input[type=datetime]', 'textarea', '[role=textbox]', 'select', '[supports-modality=keyboard]'].join(',');

		disableFocusRingByDefault();

		document.body.addEventListener('keydown', function(evt) {
			hadKeyboardEvent = true;
			setTimeout(function() {
				hadKeyboardEvent = false;
			}, 0);
		}, true);

		document.body.addEventListener('focus', function(evt) {
			if (hadKeyboardEvent || focusTriggersKeyboardModality(evt.target)) {
				document.body.setAttribute('modality', 'keyboard');
			}
		}, true);

		document.body.addEventListener('blur', function(evt) {
			document.body.removeAttribute('modality');
		}, true);

		function disableFocusRingByDefault() {
			var css = 'body:not([modality=keyboard]) :focus { outline: none; }',
				head = document.head || document.getElementsByTagName('head')[0],
				style = document.createElement('style');

			style.type = 'text/css';
			style.id = 'disable-focus-ring';
			if (style.styleSheet) {
				style.styleSheet.cssText = css;
			}
			else {
				style.appendChild(document.createTextNode(css));
			}

			head.appendChild(style);
		}

		function focusTriggersKeyboardModality(el) {
			return el.matches(keyboardModalityWhitelist);
		}
	}

	// there is no event that works consistently across start and fnft, so we utilize startup
	iaw.startup.add({
		whenDocReady: onLoadEvent.bind(this)
	});

	return {
		/**
		 * Static keycode constants.
		 */
		Keys: {
			TAB: 9,
			ENTER: 13,
			ESC: 27,
			SPACE: 32,
			PAGEUP: 33,
			PAGEDOWN: 34,
			END: 35,
			HOME: 36,
			LEFT: 37,
			UP: 38,
			RIGHT: 39,
			DOWN: 40
		},

		/**
		 * Register keys that the host application should allow to pass through to be handled by the CEP web view.
		 */
		registerKeyEventsInterest: function() {
			var keyEventsInterest = iaw.util.isWindowsOS() ? KEYS_OF_INTEREST_WIN : KEYS_OF_INTEREST_MAC;
			if (iaw.cepUtil.csInterface) {
				iaw.cepUtil.csInterface.registerKeyEventsInterest(JSON.stringify(keyEventsInterest));
			}
		},

		/**
		 * Returns true when a given element can receive keyboard or mouse focus.
		 *
		 * @param el HTMLElement An HTML element.
		 * @return Boolean true when element can receive keyboard or mouse focus.
		 */
		isFocusable: function(el) {
			return el.matches(FOCUSABLE_SELECTOR) && !isHidden(el);
		},

		/**
		 * Returns an array of focusable descendants of a given element ordered with elements having tabIndex > 0 coming before elements with tabIndex <= 0.
		 *
		 * @param el Object Parent node or selector
		 * @param tabbable Boolean Return only tabbable children by excluding elements with tabIndex < 0
		 * @param includeEl Boolean Include parent element in result if it is focusable.
		 * @return Array Ordered array of focusable elements
		 */
		focusable: function(el, tabbable, includeEl) {
			if (typeof el === 'string') {
				el = document.querySelector(el);
			}
			var basicFocusables = [],
				orderedFocusables = [],
				candidateNodelist = el.querySelectorAll(FOCUSABLE_SELECTOR),
				candidates = Array.prototype.slice.call(candidateNodelist),
				candidate, candidateTabIndex;

			if (includeEl) {
				candidates.unshift(el);
			}

			for (var i = 0, l = candidates.length; i < l; i++) {
				candidate = candidates[i];
				candidateTabIndex = candidate.tabIndex;

				if ((candidateTabIndex < 0 && tabbable) || isHidden(candidate)) {
					continue;
				}

				if (candidateTabIndex <= 0) {
					basicFocusables.push(candidate);
				}
				else {
					orderedFocusables.push({
						tabIndex: candidateTabIndex,
						node: candidate
					});
				}
			}

			orderedFocusables = orderedFocusables
				.sort(function(a, b) {
					return a.tabIndex - b.tabIndex;
				})
				.map(function(a) {
					return a.node;
				});

			Array.prototype.push.apply(orderedFocusables, basicFocusables);

			return orderedFocusables;
		},

		/**
		 * Returns an array of tabbable descendants of a given element ordered with elements having
		 * tabIndex > 0 coming before elements with tabIndex <= 0.
		 *
		 * @param el HtmlElement Parent node
		 * @param includeEl Boolean Include parent element in result if it is tabbable
		 * @return Array Ordered array of tabbable elements
		 */
		tabbable: function(el, includeEl) {
			if (typeof el === 'string') {
				el = document.querySelector(el);
			}
			return iaw.a11y.focusable(el, true, includeEl);
		},

		/**
		 * Returns true if a given element or one of its descendants has focus.
		 *
		 * @param el Object Element or selector
		 * @return Boolean Whether the element or one of its descendant has focus.
		 */
		descendantHasFocus: function(el) {
			if (typeof el === 'string') {
				el = document.querySelector(el);
			}
			return el === document.activeElement || el.contains(document.activeElement);
		},

		/**
		 * Returns next item from array of items by alphanumeric search
		 *
		 * @param evt Event Keypress event object
		 * @param items Array An array of HTMLElements
		 * @param currentItem HTMLElement Current item within the items array.
		 * @return HTMLElement An element with text content that starts with an alphanumeric string
		 */
		alphanumericSearch: function(evt, items, currentItem) {
			var charCode = evt.charCode,
				stringFromCharCode,
				index = -1;

			if (charCode <= 32 || evt.ctrlKey || evt.metaKey || evt.altKey) return;

			stringFromCharCode = String.fromCharCode(charCode);
			if (stringFromCharCode !== searchString) {
				searchString += String.fromCharCode(charCode);
			}

			clearTimeout(searchTimeout);
			searchTimeout = setTimeout(function() {
				searchString = '';
			}, searchStringDelay);

			items = items.filter(function(el) {
				var textContent = el.textContent.replace(whitespaceRegex, ' ').trim();
				return new RegExp('^' + searchString.replace(regexRegex, '\\$&'), 'i').test(textContent);
			});
			if (items.length) {
				index = items.indexOf(currentItem);
				if (index === -1) {
					return items[0];
				}
				else if (searchString.length > 1) {
					return items;
				}
				else if (index === items.length - 1) {
					return items[0];
				}
				else {
					return items[index + 1];
				}
			}
			return;
		},

		/**
		 * Returns next item from a grid/card layout in vertical direction
		 *
		 * @param items Array An array of HTMLElements
		 * @param currentItem HTMLElement Current item within the items array.
		 * @param up Boolean Search in the reverse or 'up' direction
		 * @return HTMLElement An element adjacent to the current item vertically
		 */
		getAdjacentElementVertically: function(items, currentItem, up) {
			var	increment = up ? -1 : 1,
				i = items.indexOf(currentItem),
				rect = currentItem.getBoundingClientRect(),
				rowTop = rect.top,
				colLeft = rect.left,
				colRight = rect.right,
				prevOrNext = items[i + increment],
				nextRowTop = null,
				candidates = [], filtered;

			i += increment;

			while (prevOrNext) {
				rect = prevOrNext.getBoundingClientRect();
				// determine start of next row
				if (nextRowTop === null && rowTop !== rect.top) {
					nextRowTop = rect.top;
				}
				// in next row,
				if (nextRowTop) {
					// add candidates until
					if (nextRowTop === rect.top) {
						candidates.push({item: prevOrNext, rect: rect});
					}
					// we reach the first card in the following row.
					else {
						break;
					}
				}
				i += increment;
				prevOrNext = items[i];
			}
			// sort candidates from left to right
			// @todo We may need to be aware of right-to-left languages when sorting.
			candidates = candidates.sort(function(a, b) {
				return a.rect.left > b.rect.left;
			});
			// filter candidates based whether they overlap with the current card
			filtered = candidates.filter(function(candidate) {
				rect = candidate.rect;
				return (colLeft === rect.left || colRight === rect.right ||
					(colLeft > rect.left && colRight < rect.right));
			});
			// Return the first candidate that overlaps with the current card,
			if (filtered.length) {
				return filtered[0].item;
			}
			// or return the last item in the next row.
			else if (candidates.length) {
				return candidates[candidates.length - 1].item;
			}
			return;
		},

		/**
		 * TrapFocus is used to trap keyboard focus within a DOM node.
		 * @returns Class iaw.a11y.TrapFocus class instance
		 */
		TrapFocus: function() {
			var trap,
				tabbables,
				lastFocused,
				isActive,
				config;

			/**
			 * Activate traps keyboard focus within given DOM node or selector.
			 * <p>
			 * Options:
			 * <ul>
			 *  <li><b>initialFocus:</b> By default, focus will be set to the
			 *  first element in the element's tab order, but by declaring the
			 *  <b>initialFocus</b> you can specify which specific element or
			 *  selector will receive focus.</li>
			 *  <li><b>onDeactivate:</b> A callback method to be executed when
			 *  the focus trap is deactivated</li>
			 * </ul>
			 *
			 * @param Object el HTMLselector
			 * @param Object Options object containing intialFocus and/or onDeactivate callback method.
			 */
			function activate(el, options) {
				// There can be only one focus trap at a time
				if (isActive) deactivate();
				isActive = true;

				trap = (typeof el === 'string') ? document.querySelector(el) : el;
				config = options || {};
				lastFocused = document.activeElement;

				updateTabbables();

				focus(initialFocus());

				document.addEventListener('focus', onFocus, true);
				document.addEventListener('click', onClick, true);
				document.addEventListener('keydown', onKeyDown, true);

				setModal(trap, true);
			}
			/**
			 * Returns the HTMLElement that should receive initial focus when
			 * the focus trap has been activated.
			 *
			 * @return HTMLElement Element to receive initial focus
			 */
			function initialFocus() {
				var node;

				if (!config.initialFocus) {
					node = tabbables[0];
					if (!node) {
						throw new Error('You can\'t have a focus-trap without at least one focusable element');
					}
					return node;
				}

				if (typeof config.initialFocus === 'string') {
					node = document.querySelector(config.initialFocus);
				}
				else {
					node = config.initialFocus;
				}
				if (!node) {
					throw new Error('The `initialFocus` selector you passed refers to no known node');
				}
				return node;
			}
			/**
			 * Deactivate the focus trap, call onDeactivate callback method,
			 * and restore focus to the last element that had focus before
			 * the trap was activated if the focus hasn't been shifted
			 * to some other element.
			 */
			function deactivate() {
				if (!isActive) return;
				isActive = false;

				document.removeEventListener('focus', onFocus, true);
				document.removeEventListener('click', onClick, true);
				document.removeEventListener('keydown', onKeyDown, true);

				setModal(trap, false);

				if (config.onDeactivate) {
					config.onDeactivate();
				}

				setTimeout(function() {
					if (iaw.a11y.descendantHasFocus(trap) ||
							(!document.activeElement || document.body === document.activeElement)) {
						focus(lastFocused);
					}
				}, 0);
			}
			/**
			 * Handle click event when trap is active, to ensure that elements
			 * outside focus trap do not respond to click events.
			 * @param MouseEvent evt Click event
			 */
			function onClick(evt) {
				if (trap.contains(evt.target)) return;
				evt.preventDefault();
				evt.stopImmediatePropagation();
			}

			/**
			 * Handle focus event when trap is active, to ensure that elements
			 * outside focus trap do not receive focus.
			 * @param FocusEvent evt Focus event
			 */
			function onFocus(evt) {
				updateTabbables();
				if (trap.contains(evt.target)) return;
				focus(tabbables[0]);
			}

			/**
			 * Handle key down event when trap is active to respond to the TAB
			 * key or the ESC key, which should deactivate the focus trap.
			 * @param KeyboardEvent evt Keyboard event
			 */
			function onKeyDown(evt) {
				if (evt.keyCode === iaw.a11y.Keys.TAB) {
					handleTabKey(evt);
				}

				if (evt.keyCode === iaw.a11y.Keys.ESC) {
					deactivate();
				}
			}

			/**
			 * Handle TAB key when trap is active to ensure that focus stays
			 * within the focus trap.
			 * @param KeyboardEvent evt Keyboard event
			 */
			function handleTabKey(evt) {
				evt.preventDefault();
				updateTabbables();
				var targ = evt.target;
				var index = tabbables.indexOf(targ);
				var last = tabbables[tabbables.length - 1];
				var first = tabbables[0];
				if (evt.shiftKey) {
					if (evt.target === first) {
						focus(last);
						return;
					}
					focus(tabbables[index - 1]);
					return;
				}
				if (targ === last) {
					focus(first);
					return;
				}
				focus(tabbables[index + 1]);
			}

			/**
			 * Update array of tabbable elements within the focus trap.
			 */
			function updateTabbables() {
				tabbables = iaw.a11y.tabbable(trap, true);
			}

			/**
			 * Set focus to an HTMLElement node.
			 * @param HTMLElementnode HTMLElement node to receive focus.
			 */
			function focus(node) {
				if (!node || !node.focus) return;
				if (node === initialFocus() && !iaw.a11y.isFocusable(node)) {
					node.tabIndex = -1;
					node.addEventListener('blur', function blurred() {
						node.removeAttribute('tabIndex');
						node.removeEventListener('blur', blurred);
					}, true);
				}
				node.focus();
				if (node.tagName.toLowerCase() === 'input') {
					node.select();
				}
			}

			/**
			 * Hide siblings of an HTMLElement node from assistive technology
			 * using `aria-hidden`, so that it is not possible to read elements
			 * outside of the given node with a screen reader.
			 * @param HTMLElement node HTMLElement node to receive focus.
			 */
			function setModal(node, bool) {
				if (!node) return;

				var parentNode = node.parentNode,
					siblings, sibling, cachedAriaHidden;
				while (parentNode !== document.documentElement) {
					siblings = Array.prototype.slice.call(parentNode.children);
					Array.prototype.splice.call(siblings, siblings.indexOf(node), 1);
					for (var i = 0, l = siblings.length; i < l; i++) {
						sibling = siblings[i];
						if (sibling.tagName !== 'AREA' &&
								sibling.tagName !== 'BASE' &&
								sibling.tagName !== 'BASEFONT' &&
								sibling.tagName !== 'BR' &&
								sibling.tagName !== 'COL' &&
								sibling.tagName !== 'LINK' &&
								sibling.tagName !== 'META' &&
								sibling.tagName !== 'PARAM' &&
								sibling.tagName !== 'SCRIPT' &&
								sibling.tagName !== 'STYLE') {
							if (bool) {
								cachedAriaHidden = sibling.getAttribute('aria-hidden');
								if (cachedAriaHidden) {
									sibling.setAttribute('data-aria-hidden', cachedAriaHidden);
								}
								sibling.setAttribute('aria-hidden', 'true');
							}
							else {
								cachedAriaHidden = sibling.getAttribute('data-aria-hidden');
								if (cachedAriaHidden) {
									sibling.setAttribute('aria-hidden', cachedAriaHidden);
									sibling.removeAttribute('data-aria-hidden');
								}
								else {
									sibling.removeAttribute('aria-hidden');
								}
							}
						}
					}
					node = parentNode;
					parentNode = parentNode.parentNode;
				}
			}

			return {
				activate: activate,
				deactivate: deactivate
			};
		}()
	};
}();

/*************************************************************************
 * ADOBE CONFIDENTIAL
 * ___________________
 *
 *  Copyright 2015 Adobe Systems Incorporated
 *  All Rights Reserved.
 *
 * NOTICE:  All information contained herein is, and remains
 * the property of Adobe Systems Incorporated and its suppliers,
 * if any.  The intellectual and technical concepts contained
 * herein are proprietary to Adobe Systems Incorporated and its
 * suppliers and are protected by all applicable intellectual property
 * laws, including trade secret and copyright laws.
 * Dissemination of this information or reproduction of this material
 * is strictly forbidden unless prior written permission is obtained
 * from Adobe Systems Incorporated.
 **************************************************************************/

/*global Ingest*/

/**
 * Simple singleton style container so all analytics related stuff has a centralized point.
 */
var iaw = iaw||{};

iaw.analytics = {

	/**
	 * Override to turn analytics on or off
	 */
	enabled: false,

	pipQuery: {
		//Events
		category: 'Mobile Creations',
		pipAware: {
			subCategory: 'See Start Screen',
			eventName: 'See Start Screen'
		},
		pipTry: {
			subCategory: 'Click Mobile Creations',
			eventName : 'Click Mobile Creations'
		},
		pipSucceedProject: {
			subCategory: 'Open Mobile Creations',
			eventName: 'Open Mobile Creations'
		},
		pipSucceedProjectFile: {
			subCategory: 'Open Mobile Creations',
			eventName : 'Open App Page'
		},
		pipNoAssetUI: {
			subCategory : 'No Mobile Creations',
			eventName : 'See No Asset UI'
		},
		pipGetApp: {
			subCategory : 'No Mobile Creations',
			eventName : 'Click on Get App'
		},
		pipTimeOutOnMCClick: {
			subCategory: 'TimeOutOnMCClick',
			eventName: 'TimeOutOnMCClick'
		},
		pipTimeOutOnProjectClick: {
			subCategory: 'TimeOutOnProjectClick',
			eventName: 'TimeOutOnProjectClick'
		},
		pipTimeOutOnFileClick: {
			subCategory: 'TimeOutOnFileClick',
			eventName: 'TimeOutOnFileClick'
		},
		pipTryAgainOnMCClick: {
			subCategory: 'TryAgainOnMCClick',
			eventName: 'TryAgainOnMCClick'
		},
		pipTryAgainOnProjectClick: {
			subCategory: 'TryAgainOnProjectClick',
			eventName: 'TryAgainOnProjectClick'
		},
		pipTryAgainOnFileClick: {
			subCategory: 'TryAgainOnFileClick',
			eventName: 'TryAgainOnFileClick'
		}
	},

	/**
	 * Configure analytics for future analytics calls.
	 *
	 * @param client        client ID string
	 * @param hostData      data from the host
	 * @param psdkData      data fron the PSDK
	 * @param mode          string indicating the server mode (prod, stage, or dev)
	 */
	config: function(client, hostData, psdkData, mode) {

		this.enabled = hostData.userTrackingEnabled;
		this.ingest.configure(client, hostData, psdkData, mode);
	},

	/**
	 * Compose the passed URL for proper analytics logging - this means
	 * adding in analytic query params and moving any deep links to the end.
	 * This is primarily for Adobe Learn.
	 *
	 * @param url	 original URL to add analytic params into
	 * @return String containing the URL properly ordered with analytics query arguments.
	 */
	composeURLWithAnalyticsQueryString: function(url) {

		var composedURL = url;

		if (composedURL) {
			var analyticsQuery	= this.getAnalyticsQueryString();

			// check for deep links
			var deepLinkIndex	= composedURL.indexOf('#');
			var deepLink		= '';

			if (deepLinkIndex >= 0) {
				deepLink = composedURL.substr(deepLinkIndex);
				composedURL = composedURL.substr(0, deepLinkIndex);
			}

			// check for existing query which it could have
			var querySep = ((composedURL.indexOf('?') < 0) ? '?' : '&');

			composedURL += (querySep + analyticsQuery + deepLink);
		}

		return composedURL;
	},


	/**
	 * Build the passthrough analytics string for
	 *
	 * @return String containing the query arguments.
	 */
	getAnalyticsQueryString: function() {

		var hostData = iaw.store.get('host');

		return 'locale='+hostData.language+
				'&x-product='+hostData.hostID+'%2F'+hostData.appVersion+
				'&x-product-location=CCXStart-'+hostData.displayMode+'%2F'+hostData.radarVersion+
				'&x-radarSession='+hostData.radarSessionGUID+
				'&x-appSession='+hostData.sessionGUID+
				'&ute='+hostData.userTrackingEnabled;
	},

	/**
	 * AdobePIP/Highbeam Analytics
	 */
	pip: {
		kCategory: 'CCXStart',
		fnftCategory: 'CCXFNFT',

		/**
		 * Log Highbeam events to the hosting application.
		 *
		 * @param pipCategory       Highbeam category classification string
		 * @param pipSubCategory    Highbeam sub-category classification string
		 * @param pipEvent          Highbeam event name string
		 */
		logEvent: function( pipCategory, pipSubCategory, pipEvent ) {
			if ( iaw.analytics.enabled ) {
				if ( pipEvent ) {
					// Highbeam events are limited to 128 characters
					pipEvent = (pipEvent.length < 128) ? pipEvent: pipEvent.substr(0, 127);
				}

				var pipJSO = {
					dataType: 'event',
					category: pipCategory,
					subcategory: pipSubCategory,
					eventname: pipEvent
				};

				var pipJSON = JSON.stringify( pipJSO );

				if ( iaw.cepUtil ) {
					iaw.cepUtil.sendEvent( iaw.cepUtil.events.LOGPIP, pipJSON );
				}
			}
		},

		/**
		 * Log Highbeam group data to the hosting application.
		 *
		 * @param pipGroupName		Highbeam group name
		 * @param data				Highbeam group data
		 * @param category			Highbeam group category
		 * @param subcategory		Highbeam group subcategory
		 */
		logDataGroupEvent: function( pipGroupName, data, category, subcategory ) {

			if (iaw.analytics.enabled) {
				var group_Data_Array = [];

				if (data) {
					Object.keys(data).forEach(function(key) {
						group_Data_Array.push({
							'columnname': key,
							'value': data[key].toString()
						});
					});
				}

				var pipJSON = {
					dataType: 'group',
					category: category,
					subcategory: subcategory,
					groupname: pipGroupName,
					Group_Data_Array: group_Data_Array
				};

				pipJSON = JSON.stringify( pipJSON );

				if ( iaw.cepUtil ) {
					iaw.cepUtil.sendEvent( iaw.cepUtil.events.LOGPIP, pipJSON );
				}
			}
		},

		/**
		 * Log Highbeam group data to the hosting application for FNFT. For PHXS only so far.
		 *
		 */
		logFNFTDataGroupEvent: function( pipGroupName, data ) {
			// For PHXS only so far.
			if ('PHXS' === iaw.store.get(['host', 'hostID'])) {
				this.logDataGroupEvent(pipGroupName, data, this.fnftCategory, 'Interaction');
			}
		},

		/**
		 * Utility method to log an 'Interaction' event.
		 *
		 * @param interactionEvent  event to log
		 */
		logInteractionEvent: function( interactionEvent ) {

			this.logEvent( this.kCategory, 'Interaction', interactionEvent );
		},

		/**
		 * Utility method to log an 'Interaction' event for FNFT. For PHXS only so far.
		 *
		 * @param interactionEvent  event to log
		 */
		logFNFTInteractionEvent: function( interactionEvent ) {

			if ('PHXS' === iaw.store.get(['host', 'hostID'])) {
				this.logEvent( this.fnftCategory, 'Interaction', interactionEvent );
			}
		},

		/**
		 * Utility method to log an 'Failure' event.
		 *
		 * @param failEvent         event to log
		 */
		logFailureEvent: function( failEvent ) {

			this.logEvent( this.kCategory, 'Failure', failEvent );
		}
	}, // end of pip container

	/**
	 * Ingest Analytics
	 */
	ingest: {
		/**
		 * Ingest configuration objects
		 */

		imsToken: null,
		ingestLibReady: false,
		instance: null,
		psdkData: null,

		basePayloadData: {
			eventType:			null,
			eventGUID:			null,
			dts:				null,
			userGUID:			null,
			subscriptionStatus:	null,
			productName:		null,
			productVersion:		null,
			productLanguage:	null,
			displayCount:		0,
			radarVersion:		null,
			userAgent:			navigator.userAgent,
			radarSessionGUID:	null,
			appSessionGUID:		null
		},

		renderedCards: {},  // map of rendered card events

		// since many things load asynchronously, some event actions can happen before
		// the Ingest lib scripts have completed - therefore we pre-queue some events
		// that get processed once the lib is ready
		prequeuedEvents: [],

		/**
		 * Configure Ingest for future analytics calls.
		 *
		 * @param client        client ID string
		 * @param hostData      data from the host
		 * @param psdkData      data fron the PSDK
		 * @param mode          string indicating the server mode (prod, stage, or dev)
		 */
		configure: function( client, hostData, psdkData, mode ) {
			var self = this;
			mode = mode || 'prod';
			this.psdkData = psdkData;

			// Ingest.js library configuration
			var options = {
				ENVIRONMENT: mode,
				ANALYTICS_API_KEY: client,
				ANALYTICS_X_PRODUCT: hostData.hostID+'%2F'+hostData.appVersion,
				ANALYTICS_PROJECT: 'ccxservice',
				ANALYTICS_USER_REGION: hostData.countryCode,
				ANALYTICS_INGEST_TYPE: 'dunamis',
				ANALYTICS_MAX_QUEUED_EVENTS: 50,
				ANALYTICS_DEBOUNCE: 0,
				TIMESTAMP_PROPERTY_NAME: 'event.dts_end'
			};

			var dependencies = {
				log: function(msg) {
					iaw.log.console(msg);
				},
				getAccessToken: function(callback) {
					if (self.imsToken) {
						callback(null, self.imsToken);
					}
					else { // handle case when no IMS token is available
						iaw.cepUtil.getIMSAccessToken(function( token ) {
							self.imsToken = token;
							callback(null, token);
						});
					}
				},
				clearAccessToken: function() {
					self.imsToken = null;
					iaw.cepUtil.clearAccessToken();
				}
			};
			// Get Ingest instance (Ingest supports multiple instances since v1.0)
			this.instance = Ingest.createInstance(dependencies, options);
			this.instance.enable(iaw.analytics.enabled);

			// configure basic payload
			this.basePayloadData.userGUID           = hostData.adobeGUID;
			this.basePayloadData.subscriptionStatus = hostData.accountStatus;
			this.basePayloadData.productName        = hostData.hostID;
			this.basePayloadData.productVersion     = hostData.appVersion;
			this.basePayloadData.productLanguage    = hostData.language;
			this.basePayloadData.displayCount       = hostData.displayCount;
			this.basePayloadData.radarVersion       = hostData.radarVersion;
			this.basePayloadData.modeID             = hostData.displayMode;
			this.basePayloadData.radarSessionGUID   = hostData.radarSessionGUID;
			this.basePayloadData.appSessionGUID     = hostData.sessionGUID;
			if (hostData.displayMode !== 'fnft') {
				this.basePayloadData.appLaunchCount = hostData.launchCount;
			}
			this.basePayloadData.AUMSegments        = null;
			this.basePayloadData.hvaFlow			= null;
			this.basePayloadData.displayMode		= null;
			if (psdkData) {
				this.basePayloadData.AUMSegments = psdkData.segmentID || 'none';
				this.basePayloadData.hvaFlow = psdkData.hvaFlow || 'none';

				// find the control card section for this display mode
				for (var index = 0; index < psdkData.cardControl.length && !this.basePayloadData.displayMode; index++) {
					if ( psdkData.cardControl[index].modeID === hostData.displayMode ) {
						this.basePayloadData.displayMode = psdkData.cardControl[index].cardOrder.toString();
					}
				}
			}
			// we only want to add the trialEndDts parameter if we are a trial account
			if (hostData.accountStatus !== 'paid' && hostData.secondsLeftInTrial !== undefined) {
				this.basePayloadData.trialEndDts = iaw.util.getTrialEndDate(hostData.secondsLeftInTrial);
			}

			iaw.log.console('Ingest configured ('+mode+')');
			this.ingestLibReady = true;

			// now handle any prequeued events
			this.processPrequeuedEvents();
		},

		/**
		 * Create a clone of the Ingest base payload data.
		 *
		 * @param needsAEMData		boolean flag to indicated that AEM card data is needed in the payload
		 * @return A new object based off the preset payload data.
		 */
		createPayload: function(needsAEMData) {

			var payload = JSON.parse(JSON.stringify(this.basePayloadData));

			payload.eventGUID = iaw.util.generateGUID();

			if (!needsAEMData) {
				delete payload.AUMSegments;
				delete payload.hvaFlow;
				delete payload.displayMode;
			}
			return payload;
		},

		/**
		 * If the Ingest library is not loaded/configured prior to calling a postEvent,
		 * then the event gets pre-queued. So once the library is ready then we process
		 * all the events.
		 */
		processPrequeuedEvents: function() {

			var self = this;

			this.prequeuedEvents.forEach(function(payload) {

				// update base payload properties - dont use iaw.util.assign here
				// as it would overwrite properties we dont want
				for (var prop in self.basePayloadData) {
					if (!payload[prop] && self.basePayloadData[prop]) {
						payload[prop] = self.basePayloadData[prop];
					}
				}
				self.postEvent(payload);
			});
		},

		/**
		 * Post the analytics call to the Ingest data server.
		 *
		 * @param payload           payload data to send
		 */
		postEvent: function( payload ) {
			if (this.ingestLibReady) {
				if (payload.debugIt) {
					window.alert(JSON.stringify(payload));
				}
				else {
					this.instance.postEvent(payload);
				}
			}
			else {
				this.prequeuedEvents.push(payload);
			}
		},

		/**
		 * Flush the queued events to the Ingest data server.
		 *
		 */
		flushEvent: function() {
			if (this.ingestLibReady) {
				this.instance.flush(true);
			}
		},

		/**
		 * Log a screen state event.
		 *
		 * @param mode      values: 'open', 'close-manual', 'close-auto', 'do-not-show'
		 */
		logScreenStateEvent: function( mode ) {
			var ingestPayloadData = this.createPayload();

			ingestPayloadData.eventType          = 'screen-state';
			ingestPayloadData.welcomeScreenState = mode;

			this.postEvent(ingestPayloadData);
		},

		/**
		 * Log a Ingest event on the PSDK engagement card.
		 *
		 * @param type              type of event
		 * @param cardData          card data from the PSDK
		 */
		logEngagementCardEvent: function( type, cardData ) {

			if ((type === 'rendered' && (!this.renderedCards[cardData.cardID])) ||
				(type !== 'rendered')) {
				var ingestPayloadData = this.createPayload(true);

				switch (type) {
					case 'rendered':
						ingestPayloadData.eventType = 'eng-card-rendered';
						this.renderedCards[cardData.cardID] = true;
						break;

					case 'clicked':
						ingestPayloadData.eventType = 'eng-card-click';
						break;

					default:
						iaw.log.conosle('Invalid Ingest card event');
						break;
				}
				ingestPayloadData.cardTypeID         = cardData.cardTypeID;
				ingestPayloadData.cardTypeName       = cardData.cardType;
				ingestPayloadData.cardID             = cardData.cardID;
				ingestPayloadData.cardName           = cardData.cardName;
				ingestPayloadData.width              = cardData.width;
				ingestPayloadData.displayTemplate    = cardData.displayTemplate    || '';
				ingestPayloadData.startDTS           = cardData.startDTS           || '';
				ingestPayloadData.endDTS             = cardData.endDTS             || '';
				ingestPayloadData.actionURL          = cardData.actionURL          || '';
				ingestPayloadData.urlLinkType        = cardData.urlLinkType        || '';

				// invertPresentation is a boolean - so usual logic above doesnt work
				ingestPayloadData.invertPresentation = (typeof cardData.invertPresentation !== 'undefined') ? cardData.invertPresentation: 'n/a';

				// optional params, some are not implemented yet in AEM,
				// so strip them out until they are
				if ( cardData.campaignCode ) {
					ingestPayloadData.campaignCode = cardData.campaignCode;
				}
				if ( cardData.recipe ) {
					ingestPayloadData.recipe = cardData.recipe;
				}
				if ( cardData.aumSegments ) {
					ingestPayloadData.aumSegments = cardData.aumSegments;
				}
				// price copy is only present on offer cards
				if ( cardData.priceCopy ) {
					ingestPayloadData.priceCopy = cardData.priceCopy;
				}
				// some cards like AdobeStock, may have a promo ID, if so, then log it
				if ( cardData.promoID ) {
					ingestPayloadData.promoID = cardData.promoID;
				}
				// only present on AdobeStock cards
				if ( cardData.as_query ) {
					ingestPayloadData.actionURL = ingestPayloadData.actionURL+'&'+cardData.as_query;
				}
				// playlists fields present on all engagement stream cards
				if (this.psdkData) {
					ingestPayloadData.eventParams = {
						'persona': this.psdkData.persona || 'none',
						'skill': this.psdkData.skill || 'none',
						'appLaunchBucket': this.psdkData.appLaunchBucket || 'none',
						'entitlement': this.psdkData.entitlement || 'none',
						'entitlementType': this.psdkData.entitlementType || 'none',
						'marketSegment': this.psdkData.marketSegment || 'none',
						'derivedPersona': this.psdkData.derivedPersona || 'none',
						'derivedSkill': this.psdkData.derivedSkill || 'none',
						'derivedAppLaunchBucket': this.psdkData.derivedAppLaunchBucket || 'none',
						'bonusLaunch': this.psdkData.bonusLaunch || 'none',
						'ccxVersion': this.psdkData.ccxVersion || 'none',
						'radarSessionGUID': this.psdkData.radarSessionGUID || 'none'
					};
				}

				this.postEvent(ingestPayloadData);
			}
		},

		/**
		 * Log a view change event.
		 *
		 * @param viewData           view data to log
		 */
		logViewChangeEvent: function( viewData ) {

			var ingestPayloadData = this.createPayload();

			ingestPayloadData.eventType     = 'uc-section';
			ingestPayloadData.sectionView   = viewData.sectionView;
			ingestPayloadData.sectionType   = viewData.sectionType;
			this.postEvent(ingestPayloadData);
		},

		/**
		 * Log a file/lib/etc. open event.
		 *
		 * @param itemData          item data to log
		 */
		logItemOpenedEvent: function( itemData ) {

			var ingestPayloadData = this.createPayload();

			ingestPayloadData.eventType     = 'uc-file-open';
			ingestPayloadData.fileType      = itemData.fileType;
			ingestPayloadData.fileOpenType  = itemData.openType;
			ingestPayloadData.sectionView	= itemData.sectionView;
			if (typeof itemData.itemPosition !== 'undefined') {
				ingestPayloadData.itemPosition  = itemData.itemPosition;
			}
			if (itemData.ucAction) {
				ingestPayloadData.ucAction = itemData.ucAction;
			}
			if (itemData.cardID) {
				ingestPayloadData.cardID = itemData.cardID;
			}
			if (itemData.eventAction) {
				ingestPayloadData.eventAction = itemData.eventAction;
			}
			this.postEvent(ingestPayloadData);
		},

		/**
		 * Log a miscellaneous event.
		 *
		 * @param itemData          item data to log
		 */
		logMiscellaneousEvent: function( itemData ) {

			var ingestPayloadData = this.createPayload();

			ingestPayloadData.eventType = itemData.eventType || 'uc-misc';

			for (var key in itemData) {
				ingestPayloadData[key] = itemData[key];
			}

			this.postEvent(ingestPayloadData);
		},

		/**
		 * Log a Ingest event on the Mobile Creations item.
		 *
		 * @param eventAction       render,click, or open
		 * @param cardTypeName		null/project/pages/appcards/appdetail
		 * @param cardName			will be null in most cases, added here for future expansion
		 */
		logMobileCreationsEvent: function( mcEventData ) {

			var ingestPayloadData = this.createPayload();

			ingestPayloadData.eventType	= 'mobile-creations';

			for (var prop in mcEventData) {
				if (mcEventData[prop]) {
					ingestPayloadData[prop] = mcEventData[prop];
				}
			}
			this.postEvent(ingestPayloadData);
		},

		/**
		 * Build up the event structure or the differnt types of Mobile Creations events to Ingest.
		 *
		 * @param mode			string object indicating the event mode
		 * @param action		string containg type of action 'click' or 'render'
		 * @param cardData		JSON object containing the data to log
		 */
		constructMobileCreationsEventData: function(mode, action, cardData) {

			var mcEventData = {
				eventAction:	action,
				cardTypeName:	''
			};

			// setup card data
			if (mode !== 'item') {
				for (var prop in cardData) {
					if (cardData) {
						mcEventData[prop] = cardData[prop];
					}
				}
			}

			// setup mode op
			switch (mode) {
				case 'project':
					mcEventData.cardTypeName = 'project';
					break;

				case 'pages':
					mcEventData.cardTypeName  = 'pages';
					break;

				case 'app':
					mcEventData.cardTypeName = 'appcards';
					break;

				case 'item':
					mcEventData.cardTypeName = 'item';
					mcEventData.cardName = cardData.syncGroup || null;
					mcEventData.cardID = cardData.compositeId || null;
					break;

				default:
					iaw.log.console('Invalid Ingest Mobile Creations '+action+' event');
					mcEventData = null;
					break;
			}

			return mcEventData;
		},

		/**
		 * Log a user clicked event for Mobile Creations to Ingest.
		 *
		 * @param mode			string object indicating the event mode
		 * @param cardData		JSON object containing the data to log
		 */
		logMobileCreationsRenderedEvent: function(mode, cardData) {

			var mcEventData = this.constructMobileCreationsEventData(mode, 'render', cardData);

			if (mcEventData) {
				this.logMobileCreationsEvent(mcEventData);
			}
		},

		/**
		 * Log a user clicked event for Mobile Creations to Ingest.
		 *
		 * @param mode			string object indicating the event mode
		 * @param cardData		JSON object containing the data to log
		 */
		logMobileCreationsClickedEvent: function(mode, cardData) {

			var action = (mode !== 'item') ? 'click' : 'open';
			var mcEventData = this.constructMobileCreationsEventData(mode, action, cardData);

			if (mcEventData) {
				this.logMobileCreationsEvent(mcEventData);
			}
		},

		/**
		 * Log a error event for Mobile Creations to Ingest.
		 *
		 * @param mcErrorCode	string object indicating the error code
		 */
		logMobileCreationsErrorEvent: function(mcErrorCode) {

			var mcEventData = {
				eventAction: 'error',
				errorCode : mcErrorCode
			};

			this.logMobileCreationsEvent(mcEventData);
		},

		/**
		 * Log an event from the FNFT dialog.
		 *
		 * @param itemData          item data to log
		 */
		logFNFTItemEvent: function( itemData ) {

			var ingestPayloadData = this.createPayload();

			ingestPayloadData.eventType = 'uc-file-open';
			ingestPayloadData.fileOpenType = 'new';

			iaw.util.assign(ingestPayloadData, itemData);
			this.postEvent(ingestPayloadData);
		},

		/**
		 * Log an analytics call for the FNFT grid item render event.
		 *
		 * @param itemData			grid item's data parameters
		 * @param section 			category section ID string
		 */
		logFNFTItemRenderedEvent: function( itemData ) {
			// window.alert(JSON.stringify(itemData));
			/*
				{
				   "name":"Custom (1074 x 1394 px @ 72 ppi)",
				   "tip":"Start a new Custom (1074 x 1394 px @ 72 ppi) document - 1074 x 1394 px",
				   "group":"",
				   "width":1074,
				   "height":1394,
				   "showInFNFT":false,
				   "units":"pixelsUnit",
				   "profile":"sRGB IEC61966-2.1",
				   "resolution":72,
				   "resolutionUnits":"inchesUnit",
				   "depth":8,
				   "scale":1,
				   "mode":"RGB",
				   "fill":"white",
				   "lastUsedTime":1466528752889,
				   "isPreset":true,
				   "id":"",
				   "title":"Custom (1074 x 1394 px @ 72 ppi)",
				   "description":"",
				   "thumbnail_url":"SP_PresetCustom.png",
				   "mime_type":"image/photoshop",
				   "price_prompt":"",
				   "template_category":"recent",
				   "previews":[],
				   "uuid":"f707810544c34470a5e048664fcaa5b2"
				}
				{
				   "name":"Clipboard",
				   "tip":"Start a new Clipboard document - 565 x 396 px",
				   "group":"clipboard",
				   "width":565,
				   "height":396,
				   "showInFNFT":true,
				   "units":"pixelsUnit",
				   "profile":"Display",
				   "resolution":72,
				   "resolutionUnits":"inchesUnit",
				   "depth":8,
				   "scale":1,
				   "mode":"RGB",
				   "isPreset":true,
				   "id":"",
				   "title":"Clipboard",
				   "description":"",
				   "thumbnail_url":"SP_PresetClipboard.png",
				   "mime_type":"image/photoshop",
				   "price_prompt":"",
				   "template_category":"recent",
				   "previews":[],
				   "uuid":"e258641a2e264aea9fe9b0692103457a"
				}
				{
				   "id":111631092,
				   "title":"Photo Album Presentation",
				   "description":"##### Two easy-to-use photo album mockups\r##### What's included:\r* Smart objects\r* Fully editable\r* High resolution\r* Great for presenting book layouts\r\r\r",
				   "marketing_text":null,
				   "thumbnail_url":"/Users/mortimer/Library/Caches/Adobe/CCX Welcome/stock/assets/0a529bbf-ce4d-4db3-8d1c-3d851d8ac897.jpeg",
				   "width":2048,
				   "height":1424,
				   "mime_type":"image/vnd.adobe.photoshop.template",
				   "size":16634446,
				   "template_category":[
				      "photo"
				   ],
				   "previews":[
				      {
				         "url":"/Users/mortimer/Library/Caches/Adobe/CCX Welcome/stock/assets/fbc0094f-0e27-4c79-9b76-9e87bc77fb0e.jpeg"
				      }
				   ],
				   "units":"inches",
				   "resolution":"72",
				   "price_prompt":"free",
				   "lastUsedTime":0,
				   "uuid":"a4d794f391274c32a11d02bab2005c75"
				}
			*/
			var itemPayloadData = {
				// debugIt: true,
				eventAction: 'render',
				cardTypeName: itemData.activeFilter,
				displayMode: (itemData.displayPosition >= 0) ? itemData.displayPosition : -1,
				sectionView: (itemData.group && itemData.group === 'clipboard') ? 'clipboard' : 'blank',
				displayTemplate: itemData.isPreset ? 'preset' : 'stock-template',
				cardName: itemData.name || itemData.title,
				cardID: itemData.id || 'preset'
			};

			// for templates - add in the price parameter data
			if (!itemData.isPreset) {
				itemPayloadData.attributes = { price: itemData.price_prompt };
			}
			this.logFNFTItemEvent(itemPayloadData);
		},

		/**
		 * Collected utility methods for setting FNFT sturcture data
		 */
		fnftUtil: {
			/**
			 * Utility method to set the common parameters related to both Preset and Stock template data.
			 *
			 * @param payload 				target Ingest payload object to update
			 * @param data  				source data object
			 */
			setCommonPayloadParameters: function(payload, data) {
				payload.displayMode = data.displayPosition;
				payload.cardTypeName = data.activeFilter;
				payload.cardName = data.title;
				payload.cardID = data.id || 'preset';
			},

			/**
			 * Utility method to set the common parameters related to Stock template data.
			 *
			 * @param payload 				target Ingest payload object to update
			 * @param data  				source Stock template data object
			 */
			setCommonStockPayloadParameters: function(payload, data) {
				this.setCommonPayloadParameters(payload, data);
				payload.displayTemplate = 'stock-template';
				payload.attributes = {
					price: data.price_prompt
				};
			},

			/**
			 * Utility method to set the common parameters related to Preset data.
			 *
			 * @param payload 				target Ingest payload object to update
			 * @param data  				source preset data object
			 */
			setCommonPresetPayloadParameters: function(payload, data) {
				this.setCommonPayloadParameters(payload, data);
				payload.displayTemplate = 'preset';
				payload.attributes = { };
				this.setAttributesFromPreset(payload.attributes, data);
			},

			/**
			 * Utility method to set only the parameters on the 'attributes' object that we care about.
			 *
			 * @param attributes 			target preset attributes object to update
			 * @param preset  				source preset data object
			 */
			setAttributesFromPreset: function(attributes, preset) {
				for (var key in preset) {
					switch (key) {
						case 'width':
						case 'height':
						case 'mode':
						case 'units':
						case 'profile':
						case 'fill':
						case 'resolution':
						case 'resolutionUnits':
							attributes[key] = preset[key];
							break;

						default:
							break;
					}
				}
			}
		},

		/**
		 * Log an analytics call for the FNFT action event.
		 *
		 * @param actionData		grid item's data parameters
		 */
		logFNFTActionClickedEvent: function( action, actionData ) {
			var actionPayloadData = {
				// debugIt: true,
				eventAction: 'click',
				ucAction: action
			};
			actionData = actionData || {};
			switch (action) {
				case 'preset-selected':
					this.fnftUtil.setCommonPresetPayloadParameters(actionPayloadData, actionData);
					delete actionPayloadData.ucAction;
					break;

				case 'template-selected':
					this.fnftUtil.setCommonStockPayloadParameters(actionPayloadData, actionData);
					delete actionPayloadData.ucAction;
					break;

				case 'stock-search':
					actionPayloadData.displayTemplate = 'stock-template';
					actionPayloadData.cardTypeName = actionData.activeFilter;
					actionPayloadData.actionURL = actionData.actionURL + '&as_content=ccxstart-search';
					break;

				case 'stock-sidebar':
					this.fnftUtil.setCommonStockPayloadParameters(actionPayloadData, actionData);
					actionPayloadData.actionURL = actionData.actionURL + '&as_content=ccxstart-sidebar';
					break;

				case 'render-preview':
					actionPayloadData.eventAction = 'render';
					actionPayloadData.ucAction = 'preview';
					this.fnftUtil.setCommonStockPayloadParameters(actionPayloadData, actionData);
					break;

				case 'close-template':
					actionPayloadData.ucAction = 'close';
					this.fnftUtil.setCommonStockPayloadParameters(actionPayloadData, actionData);
					break;

				case 'open-template':
					actionPayloadData.ucAction = 'open';
					this.fnftUtil.setCommonStockPayloadParameters(actionPayloadData, actionData);
					break;

				case 'preview':
				case 'preview-close':
				case 'preview-back':
				case 'license-template':
				case 'download-start':
				case 'download-end':
				case 'download':
					this.fnftUtil.setCommonStockPayloadParameters(actionPayloadData, actionData);
					break;

				case 'too-large-render':
					this.fnftUtil.setCommonStockPayloadParameters(actionPayloadData, actionData);
					actionPayloadData.ucAction = 'download-large';
					actionPayloadData.eventAction = 'render';
					break;

				case 'close':
					this.fnftUtil.setCommonPresetPayloadParameters(actionPayloadData, actionData);
					break;

				case 'preset-viewmore':
					this.fnftUtil.setCommonPresetPayloadParameters(actionPayloadData, actionData);
					actionPayloadData.ucAction = 'viewmore';
					delete actionPayloadData.attributes;
					break;

				case 'preset-create':
					this.fnftUtil.setCommonPresetPayloadParameters(actionPayloadData, actionData);
					actionPayloadData.attributesChanged = false;
					actionPayloadData.ucAction = 'create';

					// check for the 'settings' data added which indicates a preset MAY have changed
					if (actionData.settings && Object.keys(actionData.settings).length !== 0 && actionData.settings.constructor === Object) {
						actionPayloadData.attributesChanged = actionData.settings.attributesChanged || false;
						// only add changed attributes if there were actual changes
						if (actionPayloadData.attributesChanged) {
							actionPayloadData.newAttributes = {};
							this.fnftUtil.setAttributesFromPreset(actionPayloadData.newAttributes, actionData.settings);
						}
					}
					break;

				case 'more-options':
					this.fnftUtil.setCommonPresetPayloadParameters(actionPayloadData, actionData);
					iaw.util.assign(actionPayloadData, actionData.presetAttributes);
					break;

				case 'tab-selected':
					actionPayloadData.cardTypeName = actionData.activeFilter;
					break;

				case 'welcome-rendered':
					actionPayloadData.displayTemplate = 'welcome';
					actionPayloadData.eventAction = 'render';
					actionPayloadData.displayCount = actionData.displayCount;
					actionPayloadData.cardTypeName = actionData.activeFilter;
					actionPayloadData.ucAction = 'render';
					break;

				case 'welcome-closed':
					actionPayloadData.displayTemplate = 'welcome';
					actionPayloadData.cardTypeName = actionData.activeFilter;
					actionPayloadData.ucAction = 'close';
					break;

				case 'resize-dialog':
					actionPayloadData.eventAction = 'resize';
					actionPayloadData.displayTemplate = actionData.displayTemplate;
					actionPayloadData.cardName = actionData.cardName;
					actionPayloadData.cardTypeName = actionData.activeFilter;
					if (actionData.displayMode >= 0) {
						actionPayloadData.displayMode = actionData.displayMode;
					}
					actionPayloadData.attributes = actionData.attributes;
					delete actionPayloadData.ucAction;
					break;

				default:
					break;
			}
			this.logFNFTItemEvent(actionPayloadData);
		}
	} // end of ingest container
};

/*************************************************************************
 * ADOBE CONFIDENTIAL
 * ___________________
 *
 *  Copyright 2015 Adobe Systems Incorporated
 *  All Rights Reserved.
 *
 * NOTICE:  All information contained herein is, and remains
 * the property of Adobe Systems Incorporated and its suppliers,
 * if any.  The intellectual and technical concepts contained
 * herein are proprietary to Adobe Systems Incorporated and its
 * suppliers and are protected by all applicable intellectual property
 * laws, including trade secret and copyright laws.
 * Dissemination of this information or reproduction of this material
 * is strictly forbidden unless prior written permission is obtained
 * from Adobe Systems Incorporated.
 **************************************************************************/

/**
 * Simple singleton style container for holding the internationalization tables (I18N).
 */
var iaw = iaw || { };

iaw.i18n = {

	localizedStringTable:	null,
	urlLocalizationTable:	null,

	/**
	 * Normalize the Adobe locale to a standardized one. Basically converts
	 * a locale ID like ja_JP to ja-jp.
	 *
	 * @param localeID  		locale ID string to normalize
	 * @return the normalized locale ID string
	 */
	normalizeLocaleID: function(localeID) {
		return localeID ? localeID.toLowerCase().replace('_', '-') : localeID;
	},

	/**
	 * Initialize the table from a file in JSON format.
	 *
	 * @param localeID          string containing the locale to load
	 * @param continueFn        continuation function callback
	 */
	addFromLocalLocaleFile: function(localeID, continueFn) {
		// Reuse the cached strings if they are available.
		var strings = iaw.localstorage.getGlobalItem('localeStrings');
		if (strings && strings.locale === localeID && strings.version === window.buildVersion) {
			this.localizedStringTable = strings.data;

			// update html language tag for accessibility
			document.getElementsByTagName('html')[0]
				.setAttribute('lang', localeID.toLowerCase().replace(/_/, '-'));

			if (continueFn) {
				continueFn();
			}
		}
		else {
			var self = this;
			var xmlhttp = new XMLHttpRequest();

			xmlhttp.overrideMimeType('application/json');
			xmlhttp.onreadystatechange = function() {
				if (xmlhttp.readyState === 4 && xmlhttp.responseText) {
	//				xmlhttp.status == 200 && // local file will return status code 0

					if (xmlhttp.responseText) {
						self.localizedStringTable = JSON.parse(xmlhttp.responseText);
						iaw.localstorage.setGlobalItem('localeStrings', {
							locale: localeID,
							data: self.localizedStringTable,
							version: window.buildVersion
						});
					}

					// update html language tag for accessibility
					document.getElementsByTagName('html')[0]
						.setAttribute('lang', localeID.toLowerCase().replace(/_/, '-'));

					if (continueFn) {
						continueFn();
					}
				}
			};
			xmlhttp.open('GET', './locale/' + localeID + '/strings.json', true);
			xmlhttp.send();
		}
	},

	/**
	 * Add a localized string to the object's string table.
	 *
	 * @param stringID      string representing the index key into the table
	 * @param value         string value to store with associated key
	 */
	addLocalizedString: function( stringID, value ) {
		if ( stringID ) { this.localizedStringTable[stringID] = value; }
	},

	/**
	 * Retrieve a localized string to the object's string table. If the string
	 * is not present, then a standard error string is returned instead.
	 *
	 * @param stringID      string representing the index key into the table
	 * @return String value to associated with the key
	 */
	getLocalizedString: function( stringID ) {
		var locStr = this.localizedStringTable && stringID ? this.localizedStringTable[stringID] : null;
		var newStr = locStr ? locStr.replace('^n', '<br>') : '';
		return newStr;// debug ? 'NON-LOCALIZED:'+stringID : '';
	},

	/**
	 * Retrieve a localized string to the object's string table. If the string
	 * is not present, then a standard error string is returned instead.
	 *
	 * @param stringID      string representing the index key into the table
	 * @return String value to associated with the key
	 */
	getLocalizedSubstitutionString: function( stringID, subArray ) {
		var locStr = (this.localizedStringTable && stringID) ? this.localizedStringTable[stringID] : null;

		if ( locStr && subArray ) {
			for ( var index = 0; index < subArray.length; index++ ) {
				locStr = locStr.replace('^'+index, subArray[index] );
			}
		}
		else {
			locStr = '';// debug ? 'NON-LOCALIZED:'+stringID : '';
		}
		return locStr;
	},

	/**
	 * Change a document element display string to a translated string.
	 *
	 * @param elementID             document element ID
	 * @param stringID              translation key-string ID
	 * @param dontLocalize          boolean to indicate not to localize
	 */
	setDocumentElementString : function( elementID, stringID, dontLocalize ) {
		stringID = !stringID ? elementID : stringID;

		var elem = document.getElementById( elementID );

		if ( elem ) {
			elem.innerHTML = !dontLocalize ? this.getLocalizedString( stringID ) : stringID;
		}
	},

	/**
	 * Initialize the table from a file in JSON format.
	 *
	 * @param type          string containing the url type.
	 */
	addFromURLFile: function(type) {
		// Reuse the cached strings if they are available.
		if (type === 'goURL') {
			var strings = iaw.localstorage.getGlobalItem('goURL');
			if (strings && strings.version === window.buildVersion) {
				this.urlLocalizationTable = strings.data;
				return;
			}
		}
		var self    = this;
		var xmlhttp = new XMLHttpRequest();

		xmlhttp.overrideMimeType('application/json');
		xmlhttp.onreadystatechange = function() {

			if ( xmlhttp.readyState === 4 && xmlhttp.responseText ) {
//				 xmlhttp.status == 200 && // local files will return status code 0
				if ( xmlhttp.responseText ) {
					try {
						self.urlLocalizationTable = JSON.parse( xmlhttp.responseText );
					}
					catch (e) {
//						window.alert('i18n table parse error: '+e.message);
						iaw.log.exception('i18n go URL table parse error: ' + e.message);
						return;
					}
					iaw.localstorage.setGlobalItem('goURL', {
						version: window.buildVersion,
						data: self.urlLocalizationTable
					});
				}
			}
		};

		if (type === 'goURL' ) {
			xmlhttp.open('GET', './locale/'+'goURL.json', true);
		}
		xmlhttp.send();
	},

	/**
	 * Get language id for help url.
	 *
	 * @param appLang       string containing language id xx_XX from app, such like, en_US
	 * @return String value to associated with the app language.
	 */
	getLangSuffixForHelpURL : function( appLang ) {
		var helpURL =	this.urlLocalizationTable &&
						this.urlLocalizationTable.help &&
						appLang ? this.urlLocalizationTable.help[appLang] : null;

		return helpURL ? helpURL : 'en';
	},

	/**
	 * Get language id for account url.
	 *
	 * @param appLang       string containing language id xx_XX from app, such like, en_US
	 * @return String value to associated with the app language.
	 */
	getLangSuffixForAccountURL : function( appLang ) {
		var accountURL = this.urlLocalizationTable &&
						 this.urlLocalizationTable.account &&
						 appLang ? this.urlLocalizationTable.account[appLang] : null;

		return accountURL ? accountURL : 'en';
	},

	/**
	 * Get language id for mobile creations store url.
	 *
	 * @param appLang       string containing language id xx_XX from app, such like, en_US
	 * @return String value to associated with the app language.
	 */
	getLangSuffixForMobileCreationsGoURL : function( appLang ) {
		var suffix = this.urlLocalizationTable &&
					 this.urlLocalizationTable.mobilecreations &&
					 appLang ? this.urlLocalizationTable.mobilecreations[appLang] : null;

		return (suffix && suffix !== 'en') ? '_'+suffix : '';  // for enUS default is just empty
	}
};

/*************************************************************************
 * ADOBE CONFIDENTIAL
 * ___________________
 *
 *  Copyright 2015 Adobe Systems Incorporated
 *  All Rights Reserved.
 *
 * NOTICE:  All information contained herein is, and remains
 * the property of Adobe Systems Incorporated and its suppliers,
 * if any.  The intellectual and technical concepts contained
 * herein are proprietary to Adobe Systems Incorporated and its
 * suppliers and are protected by all applicable intellectual property
 * laws, including trade secret and copyright laws.
 * Dissemination of this information or reproduction of this material
 * is strictly forbidden unless prior written permission is obtained
 * from Adobe Systems Incorporated.
 **************************************************************************/
var iaw = iaw || { };

iaw.json = {
	/**
	 * Read and parse a JSON file from the local file system disk.
	 *
	 * @param path		string containing the full path to the file to process
	 * @return Processed JSON as an object or null.
	 */
	readLocalJSONFile: function(path) {

		var obj = null;

		if (window.__adobe_cep__ && path) {
			var result = window.cep.fs.readFile(path);

			if (0 === result.err) {
				try {
					obj = JSON.parse(result.data);
				}
				catch (error) {
					iaw.log.exception('Failed to read JSON file with path [' + path + '], error=' + error);
					obj = null;
				}
			}
			else if (3 === result.err) {
				iaw.log.console('Attempt to read JSON file failed, file does not exist [' + path + ']');
			}
			else {
				iaw.log.console('Attempt to read JSON file failed with path [' + path + '], error code=' + result.err);
			}
		}
		return obj;
	},

	/**
	 * Preprocess the JSON data common to all CCX extensions.
	 *
	 * @param key			data key name string
	 * @param val			key's value
	 * @return A possibly modified 'val' parameter.
	 */
	commonDataReceiver: function(key, val) {

		switch (key) {
			case 'userTrackingEnabled':
				if (typeof val === 'string') {
					val = (val === 'true' || val === '1');
				}
				iaw.analytics.enabled = val; // enable/disable analytics
				break;

			case 'fnftEnabled':
				if (typeof val === 'string') {
					val = (val === 'true' || val === '1');
				}
				break;

			case 'shortcut':
				val = val.replace(/Cmd\+/, '⌘');
				break;

			case 'language':
				// special case en_IL & en_AE to remap for en_US
				val =  (val !== 'en_IL' && val !== 'en_AE') ? val : 'en_US';
//				val = 'ja_JP';  // force Japanese
//				val = 'fr_FR';  // force French
//				val = 'de_DE';  // force German
				break;

		}
		return val;
	},

	/**
	 * Preprocess the JSON data specific for CCX-Start.
	 *
	 * @param key			data key name string
	 * @param val			key's value
	 * @return A possibly modified 'val' parameter.
	 */
	startDataReceiver: function(key, val) {

		switch (key) {
			case 'thumbnailViewEnabled':
			case 'listViewEnabled':
				if (typeof val === 'string') {
					val = (val === 'true' || val === '1');
				}
				break;

			case 'size':
				val = parseInt(val);
				break;

			case 'thumb':
				if (val) {
					// strip empty thumbnails
					if (val === 'data:image/jpeg;base64,') {
						val = '';
					}
					// strip newlines inserted by JSON.parse
					else {
						val = val.replace(/\n/g, '');
					}
				}
				break;

			case 'icon':
				if (val.indexOf('SP_Preset') < 0 && val.indexOf('SP_QuickStart') < 0) {
					switch (val) {
						case 'aep':
						case 'pr_proj_primary':
						case 'pr_convert_premiere_clip':
							val = 'CCX_Start_DefaultThumb_Pr_Ae';
							break;

						case 'ai':
						case 'psd':
						case 'id':
						case 'id_library':
						case 'id_book':
							val = 'CCX_Start_DefaultThumb_Ps_Ai_Id';
							break;

						default:
							val = 'CCX_Start_DefaultThumb_other';
							break;
					}
				}
				break;

			case 'dontShowAgain':
				if (typeof val === 'string') {
					val = (val === 'true' || val === '1');
				}
				break;

			case 'secondsLeftInTrial':
			case 'appStartClockTime':
				if (typeof val === 'string') {
					val = parseInt(val);
				}
				break;

			default:
				val = iaw.json.commonDataReceiver(key, val);
				break;
		}

		return val;
	},

	/**
	 * Preprocess the JSON data specific for CCX-FNFT.
	 *
	 * @param key			data key name string
	 * @param val			key's value
	 * @return A possibly modified 'val' parameter.
	 */
	fnftDataReceiver: function(key, val) {

		switch (key) {
			default:
				val = iaw.json.commonDataReceiver(key, val);
				break;
		}

		return val;
	}
};

/*************************************************************************
 * ADOBE CONFIDENTIAL
 * ___________________
 *
 *  Copyright 2016 Adobe Systems Incorporated
 *  All Rights Reserved.
 *
 * NOTICE:  All information contained herein is, and remains
 * the property of Adobe Systems Incorporated and its suppliers,
 * if any.  The intellectual and technical concepts contained
 * herein are proprietary to Adobe Systems Incorporated and its
 * suppliers and are protected by all applicable intellectual property
 * laws, including trade secret and copyright laws.
 * Dissemination of this information or reproduction of this material
 * is strictly forbidden unless prior written permission is obtained
 * from Adobe Systems Incorporated.
 **************************************************************************/

/**
 * Simple singleton style container so all Libray related stuff has a centralized point.
 */
var iaw = iaw || { };

iaw.libraryManager = {

	// global template lookup map
	statusLookupMap: {},
	hasSyncListener: false,

	// download status
	DOWNLOADED: 'downloaded',
	DOWNLOADING: 'downloading',
	DOWNLOAD_ERR: 'error',

	// template type and mime type
	TEMPLATE_ELEMENT_TYPE: 'application/vnd.adobe.element.template+dcx',
	MIME_TYPE: {
		'PHXS': 'image/vnd.adobe.photoshop.template',
		'ILST': 'application/illustrator.template',
		'IDSN': 'application/vnd.adobe.indesign.template'
	},

	// others
	initialized: false,
	libraryCollections: null,
	debounce: 300, // 0.3 second
	lastSyncTime: new Date().valueOf(),
	pendingSyncTimeout: null,
	checkRunningTimeout: null,
	cclibraryProcessLaunchPending: false, // CCLibrary process launch pending

	// cache licensed templates
	cachedLicensedTemplates: {},

	// __________________________________________________________________________
	/**
	 * Listener for library collections loaded
	 */
	onLibraryCollectionsLoaded: function() {
		clearTimeout(iaw.libraryManager.checkRunningTimeout);

		iaw.libraryManager.libraryCollections = ccLibraries && ccLibraries.getLoadedCollections();
		if (iaw.cepUtil.getExtensionID() !== 'com.adobe.ccx.fnft' || !iaw.libraryManager.libraryCollections) {
			return;
		}

		// Update status lookup map for fnft dialog
		iaw.libraryManager.getLicensedTemplates(iaw.store.get('host').hostID)
		.then(function(templates) {

			if (!window.__adobe_cep__) {
				iaw.fnftFakeLicensedTemplates.forEach(function(template) {
					iaw.libraryManager.statusLookupMap[template.id] = {
						templateId: template.id,
						downloadStatus: iaw.libraryManager.DOWNLOADED,
						path: template.url
					};
				});
				return;
			}

			if (!iaw.fnftFakeLicensedTemplates) {
				iaw.libraryManager.cachedLicensedTemplates = {};
			}

			templates.forEach(function(template) {
				iaw.libraryManager.statusLookupMap[template.id] = {
					templateId: template.id,
					downloadStatus: iaw.libraryManager.DOWNLOADED,
					path: template.url,
					elementRef: template.elementRef
				};
			});
		})
		.catch(function() {
			// ignore
		});

		//TODO: Enable this for ccx start screen when we need to detect cc lib changes there too.
		// Listen sync changes
		iaw.libraryManager.libraryCollections.forEach(function(libraryCollection) {
			(function(libraryCollection) {
				libraryCollection.addSyncListener(function() {
					iaw.libraryManager.onSync(libraryCollection);
				});
			})(libraryCollection);
		});
	},

	/**
	 * Load & init CC library
	 * @param filters 	an array of element filters, like ['application/vnd.adobe.element.template+dcx']
	 */
	init: function(filters) {
		filters = filters || '*';

		if (!iaw.libraryManager.isProcessInstalled()) {
			return iaw.log.console('CC Library Process is not installed.');
		}

		tophat(['cc-libraries-api.min.js'], function() {
			if (typeof ccLibraries === 'undefined') {
				return iaw.log.console('Fail to load CC Library javascript file.');
			}

			// Initialize ccLibraries
			var DEPENDENCIES = {
				log: function(message) {
					iaw.log.console(message);
				}
			};

			var OPTIONS = {
				SHARED_LOCAL_STORAGE: true,
				ELEMENT_TYPE_FILTERS: filters
			};

			ccLibraries.configure(DEPENDENCIES, OPTIONS);

			// VulcanInterface.isAppRunning is not reliable to know the running status. Sometimes it returns false for the running CC Lib Process on Mac.
			// We check if ccLibraries.isConnected in 1 second to determine the running status of CC Lib process. this workaround is suggested by CC Library team.
			iaw.libraryManager.checkRunningTimeout = setTimeout(function() {
				if ((!iaw.libraryManager.isProcessRunning()) && (!ccLibraries.isConnected())) {
					iaw.log.console('Start to launch CCLibrary Process');
					iaw.libraryManager.launchCCLibraryProcess();
				}
			}, 1000);

			ccLibraries.addLoadedCollectionsListener(iaw.libraryManager.onLibraryCollectionsLoaded);
		});

	},

    /**
	 * Get template library
	 *
	 * @return [Promise] promise that will get "Stock Templates" library
	 */
	getTemplateLibrary: function() {
		return new Promise(function(resolve, reject) {
			if (!iaw.libraryManager.isLibraryCollelctionsLoaded()) {
				iaw.log.console('Get template library: Fail to load library collections.');
				return reject();
			}

			var libraryCollection = iaw.libraryManager.libraryCollections[0];
			var templateLib;
			var libraryName = iaw.i18n.getLocalizedString('stock_template_library_name');
			libraryCollection.libraries.forEach(function(library) {
				if (!templateLib && library.name === libraryName) {
					templateLib = library;
				}
			});

			if (!templateLib) {
				templateLib = libraryCollection.createLibrary(libraryName);
			}
			resolve(templateLib);
		});
	},

	/**
	 * Util to check if representation is in downloading
	 * @param [Object] representation	CC library element's representation
	 * @return [Boolean] true or false
	 */
	isRepresentationDownloadPending: function(representation) {
		if (!representation.isExternalLink) {
			return false;
		}
		return !representation.getCachedContentPath() && !representation.getCachedExternalLinkError();
	},

	/**
	 * Util to check if element is in downloading
	 *
	 * @param [Object] element	CC library element
	 * @return [Boolean] true or false
	 */
	isElementDownloadPending: function(element) {
		return element.representations.some(function(representation) {
			return iaw.libraryManager.isRepresentationDownloadPending(representation);
		});
	},

	/**
	 * Util to retry downloading
	 *
	 * @param [Object] element	CC library element
	 */
	retryElementDownload: function(element) {
		element.representations.forEach(function(representation) {
			if (iaw.libraryManager.isRepresentationDownloadPending(representation)) {
				representation.getContentPath(); // This triggers the refetch
			}
		});
	},

	/**
	 * Util to get download progress
	 *
	 * @param element	CC library element
	 * @return [Number] download progress like 60
	 */
	getElementDownloadProgress: function(element) {
		var downloadProgress;
		element.representations.forEach(function(representation) {
			if (downloadProgress === undefined) {
				downloadProgress = representation.getExternalLinkDownloadProgress();
			}
		});
		return downloadProgress;
	},

	/**
	 * Hook CC library sync listener
	 */
	onSync: function(libraryCollection) {

		if (iaw.libraryManager.pendingSyncTimeout) {
			return;
		}

		var currentTime = new Date().valueOf();
		if (currentTime - iaw.libraryManager.lastSyncTime < iaw.libraryManager.debounce) {
			(function(libraryCollection) {
				iaw.libraryManager.pendingSyncTimeout = setTimeout(function() {
					iaw.libraryManager.pendingSyncTimeout = undefined;
					iaw.libraryManager.onSync(libraryCollection);
				}, iaw.libraryManager.debounce);
			})(libraryCollection);
			return;
		}

		iaw.libraryManager.lastSyncTime = currentTime;
		var statusLookupMap = iaw.libraryManager.statusLookupMap;

		libraryCollection.libraries.forEach(function(library) {
			library.getFilteredElements(iaw.libraryManager.TEMPLATE_ELEMENT_TYPE).forEach(function(element) {
				if (iaw.stockUtil.getElementStockLicense(element) === undefined) return;

				var id = iaw.stockUtil.getElementStockId(element);
				var status = statusLookupMap[id] || {templateId: id};
				var oldProgress = status.progress;
				status.progress = iaw.libraryManager.getElementDownloadProgress(element);
				var oldPending = status.downloadPending;
				status.downloadPending = iaw.libraryManager.isElementDownloadPending(element);

				var logPrefix = 'Stock template [' + status.templateId + '] ';
				// TODO: Split to onDownloaded, onDownloading, onDownloadError function
				if (status.downloadPending !== oldPending || status.progress !== oldProgress) {
					if (status.progress === undefined || (oldPending && !status.downloadPending)) {
						// downloaded
						// Filter out any odd progress event(s) from Library. Usually, it happens at parallel downloadings.
						if (oldProgress && status.progress && status.progress < oldProgress) return;

						element.getPrimaryRepresentation().getContentPath(function(err, path) {
							if (err || (status.downloadStatus === iaw.libraryManager.DOWNLOADED && status.path === path)) return;

							if (status.downloadStatus !== iaw.libraryManager.DOWNLOADED) {
								var templateData = iaw.util.getTemplateData(id) || {};
								iaw.analytics.ingest.logFNFTActionClickedEvent('download-end', templateData);
								iaw.log.console(logPrefix + 'is downloaded.');

								// update template data in data store.
								if (templateData) {
									templateData.licensedTime = element.modified;
									templateData.lastUsedTime = iaw.localstorage.getUserItem('templateLUT_' + id) || element.modified;
									templateData.template_category = templateData.template_category || [];
									if (templateData.template_category.indexOf('saved') === -1) {
										templateData.template_category.push('saved');
									}
								}
							}
							status.downloadStatus = iaw.libraryManager.DOWNLOADED;
							status.path = path;
							status.elementRef = element.getReference();
							iaw.store.set(['input', 'download-status'], status);
							statusLookupMap[id] = status;
						});
						return;
					}
					else if (status.progress === -1) {
						// download error
						if (status.downloadStatus === iaw.libraryManager.DOWNLOAD_ERR) return;
						status.downloadStatus = iaw.libraryManager.DOWNLOAD_ERR;
						iaw.log.console(logPrefix + 'fail to be downloaded.');
						iaw.store.set(['input', 'download-status'], status);
					}
					else if ((status.progress >= oldProgress) && status.downloadPending) {
						// downloading
						status.downloadStatus = iaw.libraryManager.DOWNLOADING;
						status.licensedTime = element.modified;
						status.lastUsedTime = element.modified;
						iaw.log.console(logPrefix + 'is being downloaded, progress=' + status.progress);
						iaw.store.set(['input', 'download-status'], status);
					}
					statusLookupMap[id] = status;
				}
				else if (status.downloadPending && (statusLookupMap[id].downloadStatus !== iaw.libraryManager.DOWNLOADING)) {
					iaw.analytics.ingest.logFNFTActionClickedEvent('download-start', iaw.util.getTemplateData(id) || {});
					status.downloadStatus = iaw.libraryManager.DOWNLOADING;
					status.progress = 0;
					statusLookupMap[id] = status;
					iaw.store.set(['input', 'download-status'], status);
				}
			});
		});
	},

	/**
	 * Build up template object based on CC library element
	 *
	 * @param [Object] element			cc library element
	 * @param [Boolean] checkPurchased	flag indicating to check purchase/license status or not
	 * @param [String] mime_type		mime type of template
	 * @return [Promise] promise that will build up template in desired format
	 */
	buildupTemplate: function(element, checkPurchased, mime_type) {

		return new Promise(function(resolve, reject) {
			if (!element || (checkPurchased && iaw.stockUtil.getElementStockLicense(element) === undefined) || iaw.libraryManager.isElementDownloadPending(element)) {
				return reject();
			}

			var representation = element.getPrimaryRepresentation();
			if (!representation) return reject();
			representation.getContentPath(function(err, templatePath) {
				if (err) return reject(err);
				element.getThumbnailPath(202, function(err, thumbnailPath) {
					if (err) {
						thumbnailPath = null;
					}
					var id = iaw.stockUtil.getElementStockId(element);
					resolve({
						id: id,
						mime_type: mime_type,
						template_category: ['saved', 'recent'],
						title: element.name,
						created: element.created,
						licensedTime: element.modified,
						modified: element.modified,
						lastUsedTime: iaw.localstorage.getUserItem('templateLUT_' + id) || element.modified,
						description: iaw.i18n.getLocalizedString('newdoc_details_template_default'),
						'thumbnail_url': thumbnailPath,
						width: representation.width,
						height: representation.height,
						url: templatePath,
						size: representation.contentLength,
						elementRef: element.getReference(),
						previews: []
					});
				});
			});
		});
	},

	/**
	 * Util to check if CC library is connected.
	 *
	 * @return [Boolean] true or false.
	 */
	isConnected: function() {
		return ccLibraries.isConnected();
	},

	/**
	 * Util to get CC library service info.
	 *
	 * @return [Object] service info.
	 */
	getServiceInfo: function() {
		return ccLibraries.getServiceInfo();
	},

	/**
	 * Util to check if CC library Process is installed
	 *
	 * @return [Boolean] true or false.
	 */
	isProcessInstalled: function() {
		return window.__adobe_cep__ ? VulcanInterface.isAppInstalled('cclibraries') : false;
	},


	// __________________________________________________________________________
	/**
	 * Public API
	 */

	/**
	 * Check if CC Library process is running or not.
	 *
	 * @return [Boolean] true or false.
	 */
	isProcessRunning: function() {
		return VulcanInterface.isAppRunning('cclibraries');
	},

	/**
	 * Launch CC library process.
	 */
	launchCCLibraryProcess: function() {
		if (iaw.libraryManager.cclibraryProcessLaunchPending) {
			return;
		}
		var interval;
		var message = 'vulcan.SuiteMessage.cclibraries.service.Initialized';

		function started() {
			if (interval) {
				clearInterval(interval);
			}
			iaw.libraryManager.cclibraryProcessLaunchPending = false;
			ccLibraries.reconnect();
			VulcanInterface.removeMessageListener(message, started);

			// Wait 1s, then check if we're still connected - if not, need to retry
			setTimeout(function() {
				if (!iaw.libraryManager.isConnected()) {
					iaw.libraryManager.launchCCLibraryProcess();
				}
			}, 1000);
		}

		function callLaunch() {
			// If we happen to already be connected then abort the interval
			if (iaw.libraryManager.isConnected()) {
				started();
				return;
			}

			// NOTE: We never try to launch the CC Library process if an update is required (either to the panel or process).
			// Unfortunately, Vulcan.isAppRunning always returns false on the Mac, which can mean we keep on trying to start the
			// process even though it's already running, and this causes 2 second hangs on the main thread in the desktop products.
			var serviceInfo = iaw.libraryManager.getServiceInfo() || {};
			if (!serviceInfo.updateRequired && iaw.libraryManager.isProcessInstalled() && !iaw.libraryManager.isProcessRunning()) {
				iaw.log.console('Launching CCLibrary Process');
				VulcanInterface.launchApp('cclibraries', false);
			}
		}

		VulcanInterface.addMessageListener(message, started);
		ccLibraries.reconnect();
		iaw.libraryManager.cclibraryProcessLaunchPending = true;

		interval = setInterval(callLaunch, 5000);
		callLaunch();
	},

	/**
	 * Get licensed templates per hostID.
	 *
	 * @param [String] hostID	host id, like 'PHXS'
	 * @return [Promise] promise that will resolve licensed templates
	 */
	getLicensedTemplates: function(hostID) {

		return new Promise(function(resolve, reject) {
			if (!window.__adobe_cep__) {
				return resolve(iaw.fnftFakeLicensedTemplates);
			}

			function onFinish(templates) {
				templates = templates.filter(function(item, pos) {
					return templates.indexOf(item) == pos;
				});

				resolve(templates);
			}

			if (!iaw.libraryManager.isLibraryCollelctionsLoaded()) {
				iaw.log.console('Get licensed templates: Fail to load library collections.');
				return reject();
			}

			var elements = [];
			var libraries = [];
			iaw.libraryManager.libraryCollections.forEach(function(libraryCollection) {
				libraries = libraries.concat(libraryCollection.libraries);
			});
			libraries.forEach(function(library) {
				elements = elements.concat(library.getFilteredElements(iaw.libraryManager.TEMPLATE_ELEMENT_TYPE));
			});

			// Filter out licensed templates via mime_type.
			// For Photoshop: it's application/photoshop.template
			// For Illustrator: it's application/illustrator.template'
			var mime_type = iaw.libraryManager.MIME_TYPE[hostID];
			if (mime_type) {
				elements = elements.filter(function(element) {
					return element.getPrimaryRepresentation().isCompatibleType(mime_type);
				});
			}

			var callCount = 0;
			var templates = [];
			if (!elements || elements.length === 0) {
				return onFinish([]);
			}

			// Build up the desired licensed template array.
			elements.forEach(function(element) {
				// build up template obj.
				iaw.libraryManager.buildupTemplate(element, true, mime_type)
				.then(function(template) {
					templates.push(template);
					if (++callCount === elements.length) {
						onFinish(templates);
					}
				})
				.catch(function() {
					if (++callCount === elements.length) {
						onFinish(templates);
					}
				});
			});
		});
	},

	/**
	 * Get templates per elements reference.
	 *
	 * @param [Array] elementRefs	an array of elements reference
	 * @return [Promise] promise that will resolve templates
	 */
	elementRefsToTemplates: function(elementRefs) {

		return new Promise(function(resolve, reject) {
			if (!elementRefs) {
				return reject();
			}

			if (!window.__adobe_cep__) {
				return resolve(iaw.fnftFakeLicensedTemplates);
			}

			function onFinish(templates) {
				resolve(templates);
			}

			if (!iaw.libraryManager.isLibraryCollelctionsLoaded()) {
				iaw.log.console('ElementRefs to templates: Fail to load library collections.');
				return reject();
			}

			var callCount = 0;
			var elements = [];
			elementRefs.forEach(function(elementRef) {
				if (!elementRef) return;
				elements.push(ccLibraries.resolveElementReference(elementRef));
			});

			var templates = [];
			if (!elements || elements.length === 0) {
				return onFinish([]);
			}
			elements.forEach(function(element) {
				// build up template obj.
				iaw.libraryManager.buildupTemplate(element)
				.then(function(template) {
					templates.push(template);
					if (++callCount === elements.length) {
						onFinish(templates);
					}
				})
				.catch(function() {
					if (++callCount === elements.length) {
						onFinish(templates);
					}
				});
			});
		});
	},

	/**
	 * Get template path per library Id.
	 *
	 * @param [Object] libraryCollection	library collection
	 * @param [String] stockTemplateId		stock template id
	 * @return [Promise] promise that will resolve path of template
	 */
	getTemplatePath: function(libraryCollection, stockTemplateId) {
		return new Promise(function(resolve, reject) {
			if (!window.__adobe_cep__) {
				var path;
				iaw.fnftFakeLicensedTemplates.forEach(function(template) {
					if (template.id === stockTemplateId) {
						path = template.url;
					}
				});
				return path;
			}
			var isFound = false;
			libraryCollection.libraries.forEach(function(library) {
				library.elements.forEach(function(element) {
					var id = iaw.stockUtil.getElementStockId(element);
					if (id === stockTemplateId && !isFound) {
						isFound = true;
						element.getPrimaryRepresentation().getContentPath(function(err, path) {
							resolve(path);
						});
					}
				});
			});
			if (!isFound) {
				resolve();
			}
		});
	},

	/**
	 * Check if temlate is licensed or not.
	 *
	 * @param [String] id	template id
	 * @return [Boolean] true or false.
	 */
	isTemplateLicensed: function(id) {
		if (!window.__adobe_cep__) {
			var license = false;
			iaw.fnftFakeLicensedTemplates.forEach(function(template) {
				if (template.id === id) {
					license = true;
				}
			});
			return license;
		}

		if (iaw.libraryManager.cachedLicensedTemplates && Object.keys(iaw.libraryManager.cachedLicensedTemplates).length > 0) {
			if (iaw.libraryManager.cachedLicensedTemplates[id] !== undefined) {
				return true;
			}
		}
		return iaw.libraryManager.statusLookupMap[id] && (iaw.libraryManager.statusLookupMap[id].downloadStatus === iaw.libraryManager.DOWNLOADED);
	},

	/**
	 * Check if temlate is in downloading.
	 *
	 * @param [String] id	template id
	 * @return [Boolean] true or false.
	 */
	isTemplateDownloading: function(id) {
		return iaw.libraryManager.statusLookupMap[id] && (iaw.libraryManager.statusLookupMap[id].downloadStatus === iaw.libraryManager.DOWNLOADING);
	},

	/**
	 * Get the status of template.
	 *
	 * @param [String] id	template id
	 * @return [Object] template status.
	 */
	getTemplateStatus: function(id) {
		return iaw.libraryManager.statusLookupMap[id];
	},

	/**
	 * Set status for template.
	 *
	 * @param [String] id		template id
	 * @param [Object] status	template status
	 */
	setTemplateStatus: function(id, status) {
		return iaw.libraryManager.statusLookupMap[id] = status;
	},

	/**
	 * Get element reference by template Id
	 *
	 * @param [String] id	template id
	 * @return [String] element reference
	 */
	getElementRefById: function(id) {
		return iaw.libraryManager.statusLookupMap[id] && iaw.libraryManager.statusLookupMap[id].elementRef;
	},

	/**
	 * Get template Id by element reference
	 *
	 * @param [String] elementRef element reference
	 * @return [String] template id
	 */
	getIdByElementRef: function(elementRef) {
		if (typeof ccLibraries !== 'undefined') {
			var element = ccLibraries && ccLibraries.resolveElementReference(elementRef);
			return element && iaw.stockUtil.getElementStockId(element);
		}
		return null;
	},

	/**
	 * Check if cc library collections get loaded
	 *
	 * @return [Boolean] true or false.
	 */
	isLibraryCollelctionsLoaded: function() {
		return (iaw.libraryManager.libraryCollections && iaw.libraryManager.libraryCollections.length > 0);
	},

	/**
	 * Get the cached licensed templates using the lookup file.
	 *
	 * @param [String] hostID	host id, like 'PHXS'
	 * @return [Promise] promise that will resolve licensed templates
	 */
	getCachedTemplates: function(hostID, resolve) {
		if (iaw.fnftFakeLicensedTemplates) {
			var templates = [];
			iaw.fnftFakeLicensedTemplates.forEach(function(template) {
				if (template['mime_type'] === iaw.libraryManager.MIME_TYPE[hostID]) {
					templates.push(template);
					iaw.libraryManager.cachedLicensedTemplates[template.id] = template;
				}
			});
			return resolve(templates);
		}

		var licensedTemplates = [];
		var csInterface = new CSInterface();
		var lookupFilePath = csInterface.getSystemPath(SystemPath.USER_DATA) + '/Adobe/Creative Cloud Libraries/LIBS/librarylookupfile';
		var lookupMapJSON = iaw.json.readLocalJSONFile(lookupFilePath);

		if (lookupMapJSON && lookupMapJSON.libraries) {

			Object.keys(lookupMapJSON.libraries).forEach(function(libId) {
				var lib = lookupMapJSON.libraries[libId];
				if (!lib || !lib.elements) return;

				Object.keys(lib.elements).forEach(function(elementId) {
					var element = lib.elements[elementId];
					if (!element || !element.reps) return;

					var template = {
						id: element.stockId,
						template_category: ['saved', 'recent'],
						title: element.name,
						created: element.mod, // no creation time so set modified time
						licensedTime: element.mod,
						modified: element.mod,
						lastUsedTime: iaw.localstorage.getUserItem('templateLUT_' + element.stockId) || element.mod,
						description: iaw.i18n.getLocalizedString('newdoc_details_template_default'),
						elementRef: 'cloud-asset://' + lib.domain + lib.path + '/' + libId + ';node=' + elementId,
						previews: []
					};

					var isWindows = iaw.store.get(['host', 'platform']) === 'win' || iaw.store.get(['host', 'platform']) === 'windows' || false;
					element.reps.forEach(function(rep) {
						if (rep && (rep.type === iaw.libraryManager.MIME_TYPE[hostID])) {
							template['mime_type'] = rep.type;
							if (isWindows) {
								template.url = rep.path.replace(/\\/g, '/');
							}
							else {
								template.url = rep.path;
							}
						}
						else if (rep && (rep.type === 'image/jpeg')) {
							if (isWindows) {
								template['thumbnail_url'] = rep.path.replace(/\\/g, '/');
							}
							else {
								template['thumbnail_url'] = rep.path;
							}
						}
					});

					if (template['mime_type'] && template.url) {
						licensedTemplates.push(template);
					}
				});
			});
		}

		licensedTemplates.forEach(function(template) {
			iaw.libraryManager.cachedLicensedTemplates[template.id] = template;
		});

		resolve(licensedTemplates);
	},

	/**
	 * Get template id from elementRef of cached templates.
	 *
	 * @param [String] elementRef element reference
	 * @return [String] template id
	 */
	getIdByCachedElementRef: function(elementRef) {
		var templates = iaw.libraryManager.cachedLicensedTemplates;
		var keys = Object.keys(templates);

		if (templates && keys.length > 0) {
			for (var i=0; i<keys.length; i++) {
				var key = keys[i];
				if (templates[key].elementRef === elementRef) {
					return templates[key].id;
				}
			}
		}
		return null;
	}
};

/*************************************************************************
 * ADOBE CONFIDENTIAL
 * ___________________
 *
 *  Copyright 2015 Adobe Systems Incorporated
 *  All Rights Reserved.
 *
 * NOTICE:  All information contained herein is, and remains
 * the property of Adobe Systems Incorporated and its suppliers,
 * if any.  The intellectual and technical concepts contained
 * herein are proprietary to Adobe Systems Incorporated and its
 * suppliers and are protected by all applicable intellectual property
 * laws, including trade secret and copyright laws.
 * Dissemination of this information or reproduction of this material
 * is strictly forbidden unless prior written permission is obtained
 * from Adobe Systems Incorporated.
 **************************************************************************/

/**
 * Simple singleton style utility container so all local storage related
 * stuff has a centralized wrapper.
 */
var iaw = iaw || { };

iaw.localstorage = {
	userID: null,

	/**
	 * Set a value to the local storage for a specified userID.
	 *
	 * @param userID            user's associated ID value
	 * @param key               local store key
	 * @param val               value to store
	 */
	setUserItem: function( key, val ) {
		if ( this.userID && key ) {
			// iaw.log.console('[iaw.localstorage.setUserItem] Setting '+key+' to '+val);
			window.localStorage[this.userID+'_'+key] = JSON.stringify(val);
		}
	},

	/**
	 * Get a value from local storage for a specified userID.
	 * If the value is not found, a null object is returned.
	 *
	 * @param userID            user's associated ID value
	 * @param key               local store key
	 * @return The stored value or null if not present.
	 */
	getUserItem: function( key ) {
		var val = null;

		if ( this.userID && key ) {
			val = window.localStorage[this.userID+'_'+key] || null;
		}

		try {
			val = JSON.parse(val);
		}
		catch (err) {
			val = null;
		}

		return val;
	},

	/**
	 * Set a value to the local storage.
	 *
	 * @param key               local store key
	 * @param val               value to store
	 */
	setGlobalItem: function( key, val ) {
		if ( key ) {
			window.localStorage[key] = JSON.stringify(val);
		}
	},

	/**
	 * Get a value from local storage.
	 * If the value is not found, a null object is returned.
	 *
	 * @param key               local store key
	 * @return The stored value or null if not present.
	 */
	getGlobalItem: function( key ) {
		var val = null;

		if ( key ) {
			val = window.localStorage[key] || null;
		}

		try {
			val = JSON.parse(val);
		}
		catch (err) {
			val = null;
		}
		
		return val;
	}
};

/*************************************************************************
 * ADOBE CONFIDENTIAL
 * ___________________
 *
 *  Copyright 2015 Adobe Systems Incorporated
 *  All Rights Reserved.
 *
 * NOTICE:  All information contained herein is, and remains
 * the property of Adobe Systems Incorporated and its suppliers,
 * if any.  The intellectual and technical concepts contained
 * herein are proprietary to Adobe Systems Incorporated and its
 * suppliers and are protected by all applicable intellectual property
 * laws, including trade secret and copyright laws.
 * Dissemination of this information or reproduction of this material
 * is strictly forbidden unless prior written permission is obtained
 * from Adobe Systems Incorporated.
 **************************************************************************/

/**
 * Simple singleton style container so all logging related stuff has
 * a centeralized wrapper.
 */
var iaw = iaw || { };

iaw.log = {

	/**
	 * Boolean flag to globally enable/disable logging.
	 */
	enabled: true,

	/**
	 * Default log prefix.
	 */
	kLogPrefix: 'CCX-Start',

	/**
	 * Send a message to the log. An optional prefix can be passed which will get
	 * prepended to the message.
	 *
	 * @param msg           string object containing the log message
	 * @param optParam      optional object containing either a string to prepend
	 *                      to the message or an object containing multiple display
	 *                      options. the format is:
	 *                          { prefix: 'string', indent: number, alert: true, trace: false }
	 */
	console : function(msg, optParam) {

		var kIndentation = '     ';
		var prefix       = '';
		var depth        = 0;
		var trace        = false;

		// parse out the optional parameters
		if (typeof optParam === 'object') {
			prefix  = (optParam.prefix) ? ' - ' + optParam.prefix   : prefix;
			depth   = (optParam.indent) ? optParam.indent           : depth;
			trace   = (optParam.trace) ? optParam.trace             : trace;
		}
		else if (typeof optParam === 'string') {
			prefix =  ' - ' + optParam;
		}

		// build up the default message
		var logmsg = this.kLogPrefix+prefix+' :: ';

		// add indentation if requested
		for (; depth > 0; depth--) { logmsg += kIndentation; }
		logmsg += msg;

		// dump to the log file
		console.log(logmsg);
		// dump the stack so we know what happened
		if (trace) console.trace();
	},

	/**
	 * Dumps a JavaScript object out to the log. An optional prefix can be passed which will get
	 * prepended to the message.  This only works in the Chrome debugger.
	 *
	 * @param msg           string object containing the log message
	 * @param prefix        optional object containing a string to prepend to the message
	 */
	dump : function(obj, prefix) {

		console.log('<< '+this.kLogPrefix+' >> object: '+ ((prefix) ? prefix + ' - %o' : '%o'), obj);
	},

	/**
	 * Shortcut to add a debugging prefixed message to the log.
	 *
	 * @param msg           string object containing the log message
	 */
	debug : function(msg) {

		this.console(msg, 'DEBUG');
	},

	/**
	 * Place a separator message in the log.
	 */
	separator : function() {

		console.log(this.kLogPrefix + '==========================================================');
	},

	/**
	 * Send a separated message out to the log . An optional prefix can be passed which
	 * will get prepended to the message.
	 *
	 * @param msg           string object containing the log message
	 * @param prefix        optional object containing a string to prepend to the message
	 */
	mark : function(msg, prefix) {

		this.separator();
		this.console('@'+Date()+' '+msg, prefix);
	},

	/**
	 * Dumps out an options structure to the log.
	 *
	 * @param msg           string object containing the log message
	 * @param obj           object to output
	 * @param step          indentation step for recursive call
	 */
	logObject : function(msg, obj, step) {

		this.console(msg);
		step = (typeof step !== 'undefined') ? step : 1;

		// walk throug the object
		if (typeof obj !== 'undefined' && obj !== null) {
			var otype = null;

			switch (typeof obj) {
				case 'object':
					for (var member in obj) {
						otype = typeof obj[member];
						this.console(member +' = '+obj[member]+' ('+otype+')', { indent: step });
					}
					break;

				// process array
				case 'array': {
					var arraylen = obj.length;

					for (var idx = 0; idx < arraylen; idx++) {
						otype = typeof obj[idx];
						this.console('array['+idx+']= '+obj[idx]+' ('+otype+')', { indent: step });
					}
					break;
				}

				default: // atomic type
					this.console('('+typeof obj+') : '+obj, { indent: step });
					break;
			}
		}
	},

	/**
	 * Dumps out a JSON object structure to the log.
	 *
	 * @param msg           string object containing the log message
	 * @param jsonData      string or object containing the JSON
	 */
	logJSON : function(msg, jsonData) {

		if (window.__adobe_cep__) {
			// CEP log changed and \n is not supported so
			// makes the data more difficult to read
			this.console(msg+' '+JSON.stringify(jsonData));
		}
		else {
			this.console(msg+'\n'+JSON.stringify(jsonData, null, 4));
		}
	},

	/**
	 * Dumps out a JSON object structure to the log.
	 *
	 * @param msg           string object containing the log message
	 * @param jsonData      string or object containing the JSON
	 */
	logJSONDelayed: function(msg, jsonData) {
		setTimeout(function() {
			iaw.log.logJSON(msg, jsonData);
		}, 1000);
	},

	/**
	 * Send an error object to the log. The error contains a message and a call stack list
	 *
	 * @param e             error object containing the exceptions
	 */
	exception : function(e) {
		console.trace();
		console.error(this.kLogPrefix+' EXCEPTION! '+ e.stack);
	}
};

/*************************************************************************
 * ADOBE CONFIDENTIAL
 * ___________________
 *
 *  Copyright 2015 Adobe Systems Incorporated
 *  All Rights Reserved.
 *
 * NOTICE:  All information contained herein is, and remains
 * the property of Adobe Systems Incorporated and its suppliers,
 * if any.  The intellectual and technical concepts contained
 * herein are proprietary to Adobe Systems Incorporated and its
 * suppliers and are protected by all applicable intellectual property
 * laws, including trade secret and copyright laws.
 * Dissemination of this information or reproduction of this material
 * is strictly forbidden unless prior written permission is obtained
 * from Adobe Systems Incorporated.
 **************************************************************************/

/*
	Only place in the app that should have a resize event handler. Add a callback to this module to receive resize events.

	Defers updates so callbacks are only called once per frame (using requestAnimationFrame), and will turn itself off after a number of frames following the last resize event.
*/
var iaw = iaw || { };

iaw.motor = {
	_running: false,
	_steps: 0,
	_cycle: 30, // number of frames to step for
	_throttleSwitch: false,
	_pistons: [],
	_stopped: false,

	start: function() {
		iaw.motor._running = false;
		this._stopped = false;
		iaw.motor._steps = 0;
		window.addEventListener('resize', iaw.motor.kick.bind(this), false);
	},

	stop: function() {
		this._stopped = true;
	},

	update: function() {
		if (this._stopped) return;
		if (iaw.motor._steps++ > iaw.motor._cycle) {
			// only run for a stride, then automatically shut off
			iaw.motor._running = false;
			return;
		}

		iaw.motor._throttleSwitch = !iaw.motor._throttleSwitch;
		if (iaw.motor._throttleSwitch) {
			// only run the pistons every other frame (30 fps throttle) because any more than that is overkill
			for (var i = 0; i < iaw.motor._pistons.length; i++) {
				var o = iaw.motor._pistons[i];
				o.func.call(o.scope || window, iaw.motor._cycle - iaw.motor._steps);
			}
		}

		window.requestAnimationFrame(iaw.motor.update);
	},

	// force an update cycle
	kick: function() {
		if (this._stopped) return;
		iaw.motor._steps = 0;
		if (iaw.motor._running) {
			return;
		}
		iaw.motor._running = true;
		iaw.motor.update();
	},

	// in order to be able to ID functions we have to hash them to generate unique-ish keys for us to find them with later
	// if we don't do this, we won't be able to remove callbacks that were bound and save us from binding callbacks multiple times all over the place
	add: function(cb, scope) {
		if (!cb) console.error('[iaw.motor.add] Pass in a valid function');
		var k = iaw.util.hashStr(cb.toString());
		var h = iaw.motor.has(k, scope);
		if (h === -1) {
			iaw.motor._pistons.push({
				func: cb,
				scope: scope,
				key: k
			});
		}
	},

	remove: function(cb) {
		var k = iaw.util.hashStr(cb.toString());
		var i = iaw.motor.has(k);
		if (i !== -1) {
			iaw.motor._pistons.splice(i, 1);
		}
	},

	// check if the handler already has iaw.motor particular callback
	has: function(k, scope) {
		var n = -1;
		var s = null;
		var i;
		for (i = 0; i < iaw.motor._pistons.length; i++) {
			n = iaw.motor._pistons[i].key;
			s = iaw.motor._pistons[i].scope;
			if (n === k && s === scope) {
				return i;
			}
		}
		return -1;
	}

	/*
		Nothing to do with motor but this has a RAF, so in order to keep track of all the RAF things, let's put them in this module.
		scrollTarget 		Element 	The element to scroll to
		[scrollDuration] 	Number 		How long the animation takes in milliseconds. Defaults to 500.
	*/
	/*scrollToTarget: function(scrollTarget, scrollContainer, scrollDuration) {

	}*/
};

/*************************************************************************
 * ADOBE CONFIDENTIAL
 * ___________________
 *
 *  Copyright 2015 Adobe Systems Incorporated
 *  All Rights Reserved.
 *
 * NOTICE:  All information contained herein is, and remains
 * the property of Adobe Systems Incorporated and its suppliers,
 * if any.  The intellectual and technical concepts contained
 * herein are proprietary to Adobe Systems Incorporated and its
 * suppliers and are protected by all applicable intellectual property
 * laws, including trade secret and copyright laws.
 * Dissemination of this information or reproduction of this material
 * is strictly forbidden unless prior written permission is obtained
 * from Adobe Systems Incorporated.
 **************************************************************************/

/**
 * Simple singleton style utility container so all utility related
 * stuff has a centralized wrapper.
 */
var iaw = iaw || { };

iaw.profile = {

	/**
	 * Configuration
	 */
	configurations: {
		stage: {
			url:	'https://cc-collab-stage.adobe.io/profile',
			expirationDays: 1
		},
		prod: {
			url:	'https://cc-collab.adobe.io/profile',
			expirationDays: 1
		}
	},
	config: null,
	retryAttempt: 	0,

	/**
	 * Fetch profile image.
	 * @param userGUID     user GUID
	 * @param token        access token
	 */
	fetchImage: function(userGUID, token, callback) {
		var self = this;
		var url;

		iaw.util.promise('GET', self.config.url, token)
		.then(function(response) {
			switch (typeof response) {
				case 'string':
					url = JSON.parse(response).user.avatar;
					break;
				case 'object':
					url = response.user.avatar;
					break;
			}
			if (!url) return callback(null);
			iaw.util.downLoadImage( url, function(blob) {
				if (!blob) {
					if (self.callback) {
						self.callback( null );
					}
					return;
				}
				var reader = new window.FileReader();

				reader.readAsDataURL( blob );
				reader.onloadend = function() {
					var imageBase64 = reader.result;

					iaw.localstorage.setUserItem('profile_image', imageBase64 );
					iaw.localstorage.setUserItem('profile_image_timestamp', new Date().getTime() );
					iaw.log.debug( '[' + url + '] Downloaded profile image for user [' + userGUID + ']' );
					callback(imageBase64);
				};
			});
			self.retryAttempt = 0;
		})
		.catch(function(err) {
			iaw.log.debug( 'Fail to download profile image with retryAttempt=' + self.retryAttempt);
			if (self.retryAttempt  === 0) {
				// access token is expired. Clear access token and retry once.
				iaw.cepUtil.clearAccessToken();
				self.getProfilePicture( userGUID, callback, ++self.retryAttempt );
			}
		});
	},

	/**
	 * Check to see if we have a profile picture and make sure
	 * it is not expired.
	 *
	 * @param userGUID     user GUID
	 * @return true if available from cache, false otherwise
	 */
	isProfilePictureAvailable: function( userGUID ) {

		var isAvailable = false;

		// make sure we have the configuration setup - if not
		// set the default to the production configuration
		var expirationDays =  this.config.expirationDays;

		// fetch image from local storage if it was cached within expirationDays
		var imageBase64 = iaw.localstorage.getUserItem('profile_image');
		var timestamp   = iaw.localstorage.getUserItem('profile_image_timestamp');

		if ( imageBase64 && timestamp ) {
			var days = ( new Date().getTime() - timestamp ) / ( 24 * 3600 * 1000 );

			isAvailable = ( timestamp && days < expirationDays );
		}
		return isAvailable;
	},

	/**
	 * Public API
	 */

	/**
	 * Get Profile picture.
	 *
	 * @param userGUID     user GUID
	 * @param callback     completion callback method
	 * @param retryAttempt retry times once token is expired
	 */
	getProfilePicture: function( userGUID, callback, retryAttempt ) {
		this.retryAttempt = retryAttempt || 0;
		this.config = this.config || (iaw.cepUtil.usingStageAuthentication() ? this.configurations.stage : this.configurations.prod);

		iaw.log.debug('Start to get profile Picture, for user [' + userGUID + ']');

		// check to see if we already have one cached
		if ( this.isProfilePictureAvailable( userGUID ) ) {
			iaw.log.debug( 'Found cached profile image for user [' + userGUID + ']');
			var imageBase64 = iaw.localstorage.getUserItem('profile_image');
			if ( callback ) { callback( imageBase64 ); }
			return;

		}

		// no cached profile image available, then access profile api to get a new one
		iaw.cepUtil.getIMSAccessToken( function( token ) {
			iaw.profile.fetchImage( userGUID, token, callback );
		});
	}
};

/*************************************************************************
 * ADOBE CONFIDENTIAL
 * ___________________
 *
 *  Copyright 2016 Adobe Systems Incorporated
 *  All Rights Reserved.
 *
 * NOTICE:  All information contained herein is, and remains
 * the property of Adobe Systems Incorporated and its suppliers,
 * if any.  The intellectual and technical concepts contained
 * herein are proprietary to Adobe Systems Incorporated and its
 * suppliers and are protected by all applicable intellectual property
 * laws, including trade secret and copyright laws.
 * Dissemination of this information or reproduction of this material
 * is strictly forbidden unless prior written permission is obtained
 * from Adobe Systems Incorporated.
 **************************************************************************/

/**
 * Simple singleton style container so all Stock related stuff has a centralized point.
 */
var iaw = iaw || { };

iaw.stockUtil = {
	STOCK_NAMESPACE: 'adobestock',
	STOCK_DATA_KEY: 'trackingdata',
	STOCK_STATE_PURCHASED: 'purchased',
	STOCK_STATE_NOT_PURCHASED: 'not_purchased',

	STOCK_URL_LICENSE: 'https://www.adobe.com/go/stocklicense_',
	STOCK_URL: 'https://stock.adobe.com',
	STOCK_URL_SEARCH: 'https://stock.adobe.com/Search',
	STOCK_URL_STAGE: 'https://primary.stock.stage.adobe.com',
	STOCK_URL_SEARCH_STAGE: 'https://primary.stock.stage.adobe.com/Search',

	/**
	 * Get Stock data from representation.
	 *
	 * @param [Object] representation	representation of cc library element
	 * @return [Object] Stock data
	 */
	getStockData: function(representation) {
		return representation && representation.getValue(this.STOCK_NAMESPACE, this.STOCK_DATA_KEY);
	},

	/**
	 * Get Stock data from cc library element.
	 *
	 * @param [Object] element 	cc library element
	 * @param [Number] index 	index of representation in cc library element
	 * @return [Object] Stock data
	 */
	getStockDataForElement: function(element, index) {
		// First try the primary representation
		var representation = index === undefined ? element.getPrimaryRepresentation() : element.representations[index];
		var stockData = this.getStockData(representation);

		if (stockData && representation.isExternalLink && representation.getCachedExternalLinkError()) {
			// It's an external link, but the external link isn't valid: Try the next representation
			return iaw.stockUtil.getStockDataForElement(element, index === undefined ? 0 : index + 1);
		}

		return stockData;
	},

	/**
	 * Get Stock Id from cc library element.
	 *
	 * @param [Object] element 	cc library element
	 * @return [String] Stock Id
	 */
	getElementStockId: function(element) {
		var stockData = this.getStockDataForElement(element);
		if (stockData) {
			return Number(stockData.content_id);
		}
		return undefined;
	},

	/**
	 * Get Stock license status from cc library element.
	 *
	 * @param [Object] element 	cc library element
	 * @return [String] Stock license status
	 */
	getElementStockLicense: function(element) {
		var stockData = this.getStockDataForElement(element);
		if (stockData) {
			if (stockData.state === this.STOCK_STATE_PURCHASED) {
				return String(stockData.license);
			}
		}
		return undefined;
	},

	/**
	 * License Stock template.
	 *
	 * @param [String] imageId 		content Id of template
	 * @param [String] licenseType 	license type of template
	 * @return [Promise] promise that will resolve the result to license template
	 */
	licenseTemplate: function(imageId, licenseType) {
		return new Promise(function(resolve, reject) {
			iaw.cepUtil.setEnvironment(iaw.cepUtil.usingStageAuthentication() ? 'stage' : 'prod');
			iaw.cepUtil.getIMSAccessToken(function(token) {
				if (!token) {
					reject('Invalid access token.');
					return;
				}
				var url = iaw.cepUtil.usingStageAuthentication() ? 'https://stock-stage.adobe.io/Rest/Libraries/1/Content/License?' : 'https://stock.adobe.ioRest/Libraries/1/Content/License?';
				url += 'content_id=' + imageId + '&license=' + licenseType;
				iaw.util.promise('GET', url, token).then(function(res) {
					if (typeof res === 'string') {
						res = JSON.parse(res);
					}
					resolve(res);
				}).catch(function(err) {
					reject(err);
				});
			});
		});
	},

	/**
	 * Create the URL to open/use for the selected Adobe Stock template.
	 * https://wiki.corp.adobe.com/display/adobestock/Jump+to+Adobe+Stock+Search+Pages
	 *
	 * @param [String] hostID	phxs, ilst, etc.
	 * @param [String] type     template type
	 * @param [String] term     search term, if any
	 * @return [String] containing the URL that was opened
	 */
	composeStockLink: function( hostID, type, term ) {
		var baseURL = iaw.cepUtil.usingStageAuthentication() ? this.STOCK_URL_SEARCH_STAGE : this.STOCK_URL_SEARCH;
		var category = -1;
		var application = 0;

		term = typeof term === 'undefined' ? '' : term;

		switch (type) {
			case 'recent':
			case 'saved':
				category = 0;
				break;
			case 'mobile':
				category = 1;
				break;
			case 'web':
				category = 2;
				break;
			case 'print':
				category = 3;
				break;
			case 'photo':
				category = 4;
				break;
			case 'film':
				category = 5;
				break;
			case 'art':
				category = 6;
				break;
			default:
				category = -1; // signify error
				break;
		}

		switch (hostID) {
			case 'PHXS':
				application = 1;
				break;
			case 'ILST':
				application = 2;
				break;
			case 'IDSN':
				application = 3;
				break;
			default:
				application = 0;
				break;
		}

		term = term ? 'k=' + iaw.util.fixedEncodeURIComponent(term) + '&' : '';
		term += iaw.analytics.getAnalyticsQueryString() + '&'; // includes language and other host data

		var query = 'filters[content_type:template]=1&filters[template_type_id][]=' + application;
		// in order to search all categories, remove the filter
		var cat = category === 0 ? '' : '&filters[template_category_id][]=' + category;
		var analyticsQuery = '&as_channel=adobe_apps&as_source=app&as_campclass=brand&as_campaign=templates_' + hostID;
		var featureFlag = 'ff_4815162342=true&';

		return (baseURL + '?' + featureFlag + term + query + cat + analyticsQuery);
	},
	
	/**
	 * Open Adobe Stock URL given the product and template type
	 * https://wiki.corp.adobe.com/display/adobestock/Jump+to+Adobe+Stock+Search+Pages
	 *
	 * @param [String] hostID	phxs, ilst, etc.
	 * @param [String] type     template type
	 * @param [String] term     search term, if any
	 * @return [String] containing the URL that was opened
	 */
	openStockLink: function( hostID, type, term ) {
		var uri = this.composeStockLink(hostID, type, term);

		if (window.__adobe_cep__) {
			iaw.util.openDefaultBrowserAuthenicated('adobeStock', uri);
		}
		else {
			window.open(uri);
		}
		return uri;
	}
};

/*************************************************************************
 * ADOBE CONFIDENTIAL
 * ___________________
 *
 *  Copyright 2015 Adobe Systems Incorporated
 *  All Rights Reserved.
 *
 * NOTICE:  All information contained herein is, and remains
 * the property of Adobe Systems Incorporated and its suppliers,
 * if any.  The intellectual and technical concepts contained
 * herein are proprietary to Adobe Systems Incorporated and its
 * suppliers and are protected by all applicable intellectual property
 * laws, including trade secret and copyright laws.
 * Dissemination of this information or reproduction of this material
 * is strictly forbidden unless prior written permission is obtained
 * from Adobe Systems Incorporated.
 **************************************************************************/

/**
 * Simple singleton style utility container so all utility related
 * stuff has a centralized wrapper.
 */
window.iaw = window.iaw || { };

window.iaw.util = {

	currentBreakPoint: 0,
	loadingLibrary: false, // flag indicates cc library is in loading or not
	libraryCallbacks: [],
	ccxProcessLaunchPending: false,


	/**
	* Get current time
	*/
	getCurrentTime: function() {
		var currentTime = new Date().getTime();
		return currentTime;
	},
	/**
	 * Determine if the current browser is running on a MS Windows based OS.
	 *
	 * @return A boolean value indicating true if the browser is on Windows,
	 *          false otherwise.
	 */
	isWindowsOS: function() {

		return ( navigator.userAgent.indexOf('Mac OS X') === -1 );
	},

	/**
	 * Convert a URL parameter string into an object.
	 *
	 * @return A object with key/value members created from the URL string.
	 */
	convertQueryString: function( search ) {

		// build out the JS object - note: this does not handle sub-objects (like the 'overrides' one)
		var jsObj =  { };

		if ( search ) {
			// remove hash before parsing the query
			var idx = search.indexOf('#');

			if (idx !== -1) {
				search = search.slice(0, idx);
			}

			var jsData = '{"' + search.replace(/&/g, '","').replace(/=/g, '":"') + '"}';

			jsObj = JSON.parse(jsData, function(key, value) {

				var val = (key === '' ? value : decodeURIComponent(value));

				// check for numeric values & boolean - otherwise assume string
				if (isNaN( val )) { // boolean, or possibly a string
					if (val !== 'true') {
						val = (val !== 'false') ? val /* its a string */ : false;
					}
					else {
						val = true;
					}
				}
				else {  // numeric
					val = parseFloat(val);
				}

				return val;
			});
		}

		return jsObj;
	},

	/**
	 * Convert query string into an object. New one because the above throws an error and it's easier to write a new one than to debug the old one.
	 * Usually you'll just want to pass in `location.hash`
	 *
	 * @return A object with key/value members created from the URL string.
	 */
	parseQueryString: function(str) {
		var ret = Object.create(null); // object with no proto so it won't break with bad param

		if (typeof str !== 'string') {
			return ret;
		}

		str = str.trim().replace(/^(\?|#|&)/, '');

		if (!str) {
			return ret;
		}

		str.split('&').forEach(function(param) {
			var parts = param.replace(/\+/g, ' ').split('=');
			// Firefox (pre 40) decodes `%3D` to `=`
			var key = parts.shift();
			var val = parts.length > 0 ? parts.join('=') : undefined;

			key = decodeURIComponent(key);

			// missing `=` should be `null`:
			val = val === undefined ? null : decodeURIComponent(val);

			if (ret[key] === undefined) {
				ret[key] = val;
			}
			else if (Array.isArray(ret[key])) {
				ret[key].push(val);
			}
			else {
				ret[key] = [ret[key], val];
			}
		});

		return ret;
	},

	/**
	 * Download image.
	 * @param url        url to download
	 * @param callback   completion callback method with the parameter of blob or null if any failure.
	 */
	downLoadImage: function( url, callback ) {

		iaw.log.debug( 'Downloading image from: ' + url );
		var xhr = new XMLHttpRequest( );

		xhr.onreadystatechange = function() {
			if ( this.readyState === 4 && this.status === 200 ) {
				iaw.log.debug( 'Image is downloaded.' );
				callback( this.response );
			}
		};
		xhr.onerror  = function( error ) {
			callback( null );
		};
		xhr.open( 'GET', url, true );
		xhr.responseType = 'blob';
		xhr.send( );
	},

	/**
	 * Setup ajax request.
	 * @param url        url to download
	 * @param callback   completion callback method with the parameter of blob or null if any failure.
	 */
	ajax: function( url, callback, scope ) {
		var xhr = new XMLHttpRequest();

		xhr.onreadystatechange = function() {
			if ( this.readyState === 4 && this.status === 200 ) {
				callback.call(scope || null, this.response);
			}
		};

		xhr.onerror = function( error ) {
			iaw.log.debug('[util.ajax] Error');
			callback.call(scope || null, error);
		};
		xhr.open('GET', url, true);
		xhr.send();
	},

	/**
	 *
	 */
	normalize: function(v, min, max) {

		return (v - min) / (max - min);
	},

	/**
	 * Open a URL in the default browser.
	 *
	 * @param url string containing the URL to load
	 */
	openDefaultBrowser: function( url, isAuthenticated ) {

		isAuthenticated = isAuthenticated || false;

		// skip the authenticated URL's because they dont mean anything... we want the base
		if ( !isAuthenticated ) {
			iaw.analytics.pip.logInteractionEvent( 'OpenExtLink:' + url );
		}
		if ( window.cep.util.openURLInDefaultBrowser ) {
			window.cep.util.openURLInDefaultBrowser(url);
		}
		else {
			window.open(url, '_blank');
		}
	},

	/**
	 * Similar to openDefaultBrowser but uses creates an authenicated
	 * jump url.
	 *
	 * @param clientKey		element key that matches entry in iaw.cepUtil.authenticationInfo
	 * @param url			string containing the URL to open
	 */
	openDefaultBrowserAuthenicated: function( clientKey, url ) {

		if ( iaw.cepUtil.imsValid() ) {
			var query = url.indexOf('?');

			iaw.analytics.pip.logInteractionEvent( 'OpenExtLink:' + url.substr(0, (query > 0) ? query : url.length) );

			var callback = function(authenticatedURL) { //eslint-disable-line func-style
				iaw.log.console('The generated jumpURL is: ' + authenticatedURL);
				iaw.util.openDefaultBrowser( authenticatedURL, true );
			};
			this.jumpToURL( iaw.cepUtil.authenticationInfo[clientKey].clientID,
							iaw.cepUtil.authenticationInfo[clientKey].clientScope,
							url, callback );
		}
		else {
			this.openDefaultBrowser( url );
		}
	},

	/**
	 * Set the UI theme mode.
	 *
	 * @param mode          color mode, 'light' or 'dark'
	 */
	setUIThemeMode: function( mode ) {

		switch ( mode ) {
			case 'light':
				document.body.classList.remove('spc--dark');
				break;

			case 'dark':
				document.body.classList.add('spc--dark');
				break;

			default:
				// get the interface color -- if CEP is not present -
				// submit to the dark side cause its cooler...
				var interfaceColor = ( iaw.cepUtil.csInterface ) ? iaw.cepUtil.getUIThemeColor() : null;

				this.setUIThemeMode( (interfaceColor && interfaceColor.red >= 184) ? 'light' : 'dark' );
				break;
		}
	},

	/**
	 * Method to generate a jump url. Only for CEP based applications. This method
	 * will make a callback to deal with the jump url.
	 *
	 * @param clientID      SSO client ID
	 * @param clientScope   SSO client scope
	 * @param url           base url
	 * @param callback      callback method which takes the following parameters:
	 *                          the jump url to deal with
	 */
	jumpToURL : function( clientID, clientScope, url,  callback) {

		if ( url && callback && clientID && clientScope ) {
			if (window.__adobe_cep__) {
				iaw.cepUtil.getIMSAccessToken( function(token) {
					if ( token ) {
						iaw.cepUtil.createJumpURL(clientID, clientScope, url, callback);
					}
					else {
						iaw.log.console('No jump URL since access token is invalid.');
						callback(url);
					}
				});
			}
			else { // no CEP present
			/*
				$.get('https://'+ getEnvironment().ims +'/ims/jumptoken/v1',
					  {
					  bearer_token:accessToken,
					  target_client_id:'AdobeStockClient1',
					  target_redirect_uri:url,
					  target_scope:'AdobeID,openid,creative_cloud,read_organizations,gnav,additional_info.address.mail_to,sao.stock'
					  })
				.success(function(data) {
						 if (data.jump) {
						 HelloLog.log('jump url:' + data.jump);
						 callback(data.jump);
						 }
						 })
				.error(function(jqXHR, textStatus, errorThrown) {
					   console.log('jump url error: ' + errorThrown);
					   callback(url);
					   });
				*/
			}
		}
	},

	/**
	 * Method to append an external script to the head
	 *
	 * @param script        script path
	 * @return [Promise] promise that will resolve when the script is loaded
	 */
	loadExternalScript : function( script ) {
		return new Promise(function(resolve, reject) {
			var scriptTag = document.createElement('script');
			// setup properties so it's interpreted correctly and loads asynchronously
			scriptTag.type = 'text/javascript';
			scriptTag.charset = 'utf-8';
			// ASYNC: load in parallel and execute as soon as possible
			scriptTag.async = true;
			// DEFER: load in parallel but maintain execution order
			scriptTag.defer = false;
			scriptTag.src = script;
			scriptTag.onload = function() {
				resolve(scriptTag);
			};
			scriptTag.onerror = function(error) {
				reject(error);
			};
			// kick off the load, directly to body so we don't have to traverse to find the head
			document.body.appendChild(scriptTag);
		});
	},

	/**
	 * Format the a Date object in the correct string layout for Radar.
	 * This method is a percaution in case someone changes the required
	 * date format, and we won't need to hunt throught the code to change
	 * all the instances of moment.format
	 *
	 * @param [Date] stamp     Date time stamp to format
	 * @return [String] in the date format YYYY-MM-DDTHH:mm:ss.SSS-XXXX
	 */
	formatTimeStamp: function formatTimeStamp(date) {
		function pad(n, count) {
			count = count || 2;
			var def = '';

			for (var i = 0; i < count; ++i) {
				def += '0';
			}
			return String(def + n).slice(-count);
		}

		var offset = date.getTimezoneOffset();

		return (
			date.getFullYear() +
			'-' +
			pad(date.getMonth() + 1) +
			'-' +
			pad(date.getDate()) +
			'T' +
			pad(date.getHours()) +
			':' +
			pad(date.getMinutes()) +
			':' +
			pad(date.getSeconds()) +
			'.' +
			pad(date.getMilliseconds(), 3) +
			(offset < 0 ? '+' : '-') +
			pad(Math.abs(offset) / 0.6, 4)
		);
	},

	/**
	 * Method to get trial end date.
	 *
	 * @param secondsLeftInTrial      seconds left in trial
	 */
	getTrialEndDate : function( secondsLeftInTrial ) {

		var trialEndDate = new Date(new Date().getTime() + secondsLeftInTrial * 1000);

		return (secondsLeftInTrial >= 0) ? this.formatTimeStamp(trialEndDate) : null;
	},

	/**
	 * Generate a rfc4122 version 4 compliant GUID.
	 * @see http://stackoverflow.com/questions/105034/create-guid-uuid-in-javascript
	 *
	 * @return A string containing the GUID
	 */
	generateGUID : function() {

		/*jshint bitwise: false*/
		return 'xxxxxxxxxxxx4xxxyxxxxxxxxxxxxxxx'.replace(/[xy]/g, function(c) {
			// 'x|0' is a shortcut for 'Math.floor(x)'
			var r = Math.random()*16|0;
			var v = (c === 'x') ? r : (r&0x3|0x8);

			return v.toString(16);
		});
	},

	/**
	 * Open the Library panel to a specific library
	 *
	 * @param The ID of the library you want to show
	 */
	showLibraryInPanel : function( libraryId ) {

		var csInterface = new CSInterface();
		var emitEvent   = function() { //eslint-disable-line func-style
			var setLibEvent = new CSEvent('dlSetCurrentLibrary', 'APPLICATION');

			setLibEvent.data = {'libraryId' : libraryId};
			csInterface.dispatchEvent(setLibEvent);
		};

		var handlePanelInitialized = function() { //eslint-disable-line func-style
			csInterface.removeEventListener('dlPanelInitialized', handlePanelInitialized);
			setTimeout(emitEvent, 200);
		};

		// Emit the event once incase the panel is already open
		emitEvent();

		// Emit the event after panel is initialized
		csInterface.addEventListener('dlPanelInitialized', handlePanelInitialized);

		// Launch the extension incase if it's not already open
		csInterface.requestOpenExtension('com.adobe.DesignLibraries.angular', '');
	},

	/**
	 * Promise helper function
	 *
	 * @param The method you want to use
	 * @param The URL of the service
	 * @param More http headers
	 * @param An array of ignored http status
	 */
	promise : function( method, url, token, headers, ignoredStatus ) {

		return new Promise(function(resolve, reject) {
			var request = new XMLHttpRequest();
			request.open(method, url, true);

			request.onload = function() {
				if (request.status === 200 || request.status === 202) {
					resolve(request.response);
				}
				else if (ignoredStatus && ignoredStatus.indexOf(request.status) > -1) {
					resolve(request.status);
				}
				else {
					reject(request.statusText);
				}
			};

			request.onerror = function() {
				reject(request.statusText || 'Network Error');
			};

			if (token) {
				request.setRequestHeader('x-api-key', 'CCXInAppWelcome');
				request.setRequestHeader('Content-Type', 'application/json');
				request.setRequestHeader('Authorization', 'Bearer ' + token);
				if (headers) {
					Object.keys(headers).forEach(function(key) {
						if (typeof headers[key] !== 'undefined') {
							request.setRequestHeader(key, headers[key]);
						}
					});
				}
			}
			request.send();
		});
	},

	/**
	 * Handle status coming back from the host ExtendScript method,
	 * closes the extension is status is 'true'. Remember callbacks/continuation
	 * methods from ExtendScript always send params as strings.
	 *
	 * @param status       string containing 'false' if cancelled/error, 'true' otherwise
	 */
	closeExtOnStatus: function(status) {
		if ((status.toLowerCase() === 'true') && window.__adobe_cep__) {
			iaw.analytics.ingest.logScreenStateEvent('close-auto');
			iaw.cepUtil.sendEvent(iaw.cepUtil.events.REQUESTHOSTCLOSE, null);
		}
	},

	/**
	 * To be more stringent in adhering to RFC 3986 (which reserves !, ', (, ), and *),
	 * even though these characters have no formalized URI delimiting uses.
	 *
	 * @param str			string to encode
	 * @return String object encoded for URI
	 * @see https://developer.mozilla.org/en-US/docs/Web/JavaScript/Reference/Global_Objects/encodeURIComponent
	 */
	fixedEncodeURIComponent: function(str) {
		var encoded = (str && str.length) ? encodeURIComponent(str).replace(/[!'()*]/g, function(c) {
			return '%' + c.charCodeAt(0).toString(16);
		}) : str;

		return encoded;
	},

	/**
	 * Round to the nearest decimal
	 *
	 * @param {Number} num
	 * @param {Number} decimal - how many decimal places you want to round to
	 * @return {Number} - number rounded to the correct number of decimals
	 */
	roundDecimal: function(num, decimal) {
		return Number(Math.round(Number(num).toFixed(20) + ('e' + decimal)) + ('e-' + decimal));
	},

	/**
	 * Using the ceiling of the closest decimal
	 * Example: ceilDecimal(0.131, 2); // 0.14
	 *
	 * @param {Number} num
	 * @param {Number} decimal - how many decimal places you want to round to
	 * @return {Number} - number rounded to the correct number of decimals
	 */
	ceilDecimal: function(num, decimal) {
		return Number(Math.ceil(Number(num).toFixed(20) + ('e' + decimal)) + ('e-' + decimal));
	},

	/**
	 * Using the floor of the closest decimal
	 * Example: floorDecimal(0.131, 2); // 0.13
	 *
	 * @param {Number} num
	 * @param {Number} decimal - how many decimal places you want to round to
	 * @return {Number} - number rounded to the correct number of decimals
	 */
	floorDecimal: function(num, decimal) {
		return Number(Math.floor(Number(num).toFixed(20) + ('e' + decimal)) + ('e-' + decimal));
	},

	/**
     *
     *
     * @param target 	If string, will mount the tag to first element matched with querySelector; else, assume it's an element
     *
	 */
	mountTag: function(target, tagName, data, animClassIn, animClassOut) {
		data = data || null;
		animClassOut = animClassOut || 'anim--fade-out';
		animClassIn = animClassIn || 'anim--fade-in';

		var parent = target;
		if (typeof parent === 'string') {
			parent = document.querySelector(target);
			if (!parent) {
				console.warn('[iaw.util.mountTag] No element found with selector '+target);
				return;
			}
		}

		function onEnd(evt) {
			// console.log('[mount] onEnd');
			parent.removeEventListener('webkitTransitionEnd', onEnd);
			parent.removeEventListener('webkitAnimationEnd', onEnd);
			parent.removeEventListener('animationend', onEnd);

			parent.classList.remove(animClassOut);
			parent.classList.add(animClassIn);
			riot.mount(parent, tagName, data);
		}

		// just in case it was there
		parent.classList.remove('hidden');
		parent.classList.remove(animClassOut);
		parent.classList.remove(animClassIn);

		if (parent.children.length > 0 || parent.attributes['data-is']) {
			// if there's already something in here, fade it out
			parent.addEventListener('webkitTransitionEnd', onEnd, false);
			parent.addEventListener('webkitAnimationEnd', onEnd, false);
			parent.addEventListener('animationend', onEnd, false);

			parent.classList.add(animClassOut);
		}
		else {
			// animate it in immediately
			parent.classList.add(animClassIn);
			riot.mount(parent, tagName, data);
		}
	},

	/**
     *
     *
     * @param tagInstance
     *
	 */
	unmountTag: function(tagInstance, animClassIn, animClassOut) {
		animClassOut = animClassOut || 'anim--fade-out';
		animClassIn = animClassIn || 'anim--fade-in';
		var parent = tagInstance.root;

		if (parent.classList.contains(animClassOut)) {
			// it's already animating out, so ignore
			return;
		}

		// just in case it was there
		parent.classList.remove(animClassIn);

		function onEnd(evt) {
			// console.log('[unmount] onEnd');
			parent.removeEventListener('webkitTransitionEnd', onEnd);
			parent.removeEventListener('webkitAnimationEnd', onEnd);
			parent.removeEventListener('animationend', onEnd);

			tagInstance.unmount(true);
			parent.removeAttribute('data-is'); // must be a riot bug, because riot should remove this for us
			parent.classList.remove(animClassOut); // clean classList for next time
			parent.classList.add('hidden'); // remove from DOM
		}

		parent.addEventListener('webkitTransitionEnd', onEnd, false);
		parent.addEventListener('webkitAnimationEnd', onEnd, false);
		parent.addEventListener('animationend', onEnd, false);

		parent.classList.add(animClassOut);
	},

	showElement: function(element, animClassIn, animClassOut) {
		animClassOut = animClassOut || 'anim--fade-out';
		animClassIn = animClassIn || 'anim--fade-in';

		// just in case it was there
		element.classList.remove('hidden');
		element.classList.remove(animClassOut);
		element.classList.remove(animClassIn);

		element.classList.add(animClassIn);
	},

	hideElement: function(element, animClassIn, animClassOut) {
		animClassOut = animClassOut || 'anim--fade-out';
		animClassIn = animClassIn || 'anim--fade-in';

		// just in case it was there
		element.classList.remove(animClassOut);
		element.classList.remove(animClassIn);

		function onEnd(evt) {
			element.removeEventListener('webkitTransitionEnd', onEnd);
			element.removeEventListener('webkitAnimationEnd', onEnd);
			element.removeEventListener('animationend', onEnd);
			element.classList.add('hidden'); // remove from DOM
		}

		element.addEventListener('webkitTransitionEnd', onEnd, false);
		element.addEventListener('webkitAnimationEnd', onEnd, false);
		element.addEventListener('animationend', onEnd, false);

		element.classList.add(animClassOut);
	},

	/**
     * Convert a country code to a shared cloud useable region
     *
     * @param countryCode    country code
     * @return two digit region used in shared cloud call
     *
	 */
	getRegionFromCountryCode: function(countryCode) {
		return iaw.util.countryCodeMap[countryCode];
	},
	// keep this outside so it's only created once per app session, not once every time the function is called, or else we'll have a memory problem
	countryCodeMap: {'US': 'us', 'CA': 'us', 'AI': 'us', 'AG': 'us', 'AR': 'us', 'AW': 'us', 'BS': 'us', 'BB': 'us', 'BZ': 'us', 'BM': 'us', 'BO': 'us', 'BR': 'us', 'KY': 'us', 'CL': 'us', 'CO': 'us', 'CR': 'us', 'DM': 'us', 'DO': 'us', 'EC': 'us', 'SV': 'us', 'FK': 'us', 'GF': 'us', 'GD': 'us', 'GP': 'us', 'GT': 'us', 'GY': 'us', 'HT': 'us', 'HN': 'us', 'JM': 'us', 'MQ': 'us', 'MX': 'us', 'MS': 'us', 'AN': 'us', 'NI': 'us', 'PA': 'us', 'PY': 'us', 'PE': 'us', 'KN': 'us', 'LC': 'us', 'PM': 'us', 'VC': 'us', 'GS': 'us', 'SR': 'us', 'TT': 'us', 'TC': 'us', 'UM': 'us', 'UY': 'us', 'VE': 'us', 'VG': 'us', 'AS': 'us', 'PR': 'us', 'GB': 'eu', 'AL': 'eu', 'DZ': 'eu', 'AD': 'eu', 'AO': 'eu', 'AM': 'eu', 'AT': 'eu', 'AZ': 'eu', 'BY': 'eu', 'BE': 'eu', 'BJ': 'eu', 'BA': 'eu', 'BW': 'eu', 'IO': 'eu', 'BG': 'eu', 'BF': 'eu', 'BI': 'eu', 'CM': 'eu', 'CV': 'eu', 'CF': 'eu', 'TD': 'eu', 'KM': 'eu', 'CD': 'eu', 'CG': 'eu', 'HR': 'eu', 'CY': 'eu', 'CZ': 'eu', 'DK': 'eu', 'DJ': 'eu', 'EG': 'eu', 'GQ': 'eu', 'ER': 'eu', 'EE': 'eu', 'ET': 'eu', 'FO': 'eu', 'FI': 'eu', 'FR': 'eu', 'GA': 'eu', 'GM': 'eu', 'GE': 'eu', 'DE': 'eu', 'GH': 'eu', 'GI': 'eu', 'GR': 'eu', 'GL': 'eu', 'GN': 'eu', 'GW': 'eu', 'HU': 'eu', 'IS': 'eu', 'IE': 'eu', 'IT': 'eu', 'CI': 'eu', 'KE': 'eu', 'LV': 'eu', 'LS': 'eu', 'LR': 'eu', 'LY': 'eu', 'LI': 'eu', 'LT': 'eu', 'LU': 'eu', 'MK': 'eu', 'MG': 'eu', 'MW': 'eu', 'ML': 'eu', 'MT': 'eu', 'MR': 'eu', 'MU': 'eu', 'YT': 'eu', 'MD': 'eu', 'MC': 'eu', 'ME': 'eu', 'MA': 'eu', 'MZ': 'eu', 'NA': 'eu', 'NL': 'eu', 'NE': 'eu', 'NG': 'eu', 'NO': 'eu', 'PS': 'eu', 'PL': 'eu', 'PT': 'eu', 'RE': 'eu', 'RO': 'eu', 'RW': 'eu', 'SH': 'eu', 'SM': 'eu', 'ST': 'eu', 'SN': 'eu', 'CS': 'eu', 'RS': 'eu', 'SC': 'eu', 'SL': 'eu', 'SK': 'eu', 'SI': 'eu', 'SO': 'eu', 'ZA': 'eu', 'ES': 'eu', 'SJ': 'eu', 'SZ': 'eu', 'SE': 'eu', 'CH': 'eu', 'TZ': 'eu', 'TG': 'eu', 'TN': 'eu', 'UG': 'eu', 'UA': 'eu', 'VA': 'eu', 'EH': 'eu', 'ZM': 'eu', 'ZW': 'eu', 'AF': 'ap', 'AQ': 'ap', 'AU': 'ap', 'BH': 'ap', 'BD': 'ap', 'BT': 'ap', 'BN': 'ap', 'MM': 'ap', 'KH': 'ap', 'CN': 'ap', 'CX': 'ap', 'CC': 'ap', 'CK': 'ap', 'TL': 'ap', 'FJ': 'ap', 'PF': 'ap', 'HK': 'ap', 'IN': 'ap', 'ID': 'ap', 'IQ': 'ap', 'IL': 'ap', 'JP': 'ap', 'JO': 'ap', 'KZ': 'ap', 'KI': 'ap', 'KR': 'ap', 'KW': 'ap', 'KG': 'ap', 'LA': 'ap', 'LB': 'ap', 'MO': 'ap', 'MY': 'ap', 'MV': 'ap', 'MH': 'ap', 'FM': 'ap', 'MN': 'ap', 'NR': 'ap', 'NP': 'ap', 'NC': 'ap', 'NZ': 'ap', 'NU': 'ap', 'NF': 'ap', 'OM': 'ap', 'PK': 'ap', 'PG': 'ap', 'PH': 'ap', 'PN': 'ap', 'QA': 'ap', 'RU': 'ap', 'WS': 'ap', 'SA': 'ap', 'SG': 'ap', 'SB': 'ap', 'LK': 'ap', 'TW': 'ap', 'TJ': 'ap', 'TH': 'ap', 'TK': 'ap', 'TO': 'ap', 'TR': 'ap', 'TM': 'ap', 'TV': 'ap', 'AE': 'ap', 'UZ': 'ap', 'VU': 'ap', 'VN': 'ap', 'WF': 'ap', 'YE': 'ap'},

	hashStr: function(str) {
		var hash = 0, i, chr, len;
		if (str.length === 0) return hash;
		for (i = 0, len = str.length; i < len; i++) {
			chr = str.charCodeAt(i);
			hash = ((hash << 5) - hash) + chr;
			hash |= 0; // convert to 32bit integer
		}
		return hash;
	},

	/**
	 * Convert the file size into a formatted string.
	 *
	 * @param isize			integer value of file size
	 * @return A string formatted with the size MB/GB/KB etc. specifier
	 */
	createFileSizeString: function(isize) {
		var val = '';

		if (isize >= 1099511627776) { // TB
			val = ((isize / 1099511627776).toFixed(1)).toString()+iaw.i18n.getLocalizedString('filesize_key_TB');
		}
		else if (isize >= 1073741824) { // GB
			val = ((isize / 1073741824).toFixed(1)).toString()+iaw.i18n.getLocalizedString('filesize_key_GB');
		}
		else if (isize >= 1048576) { // MB
			val = ((isize / 1048576).toFixed(1)).toString()+iaw.i18n.getLocalizedString('filesize_key_MB');
		}
		else if (isize >= 1024) { // KB
			val = ((isize / 1024).toFixed(1)).toString() + iaw.i18n.getLocalizedString('filesize_key_KB');
		}
		else if (isize > 0) { // Bytes
			val = isize.toString()+iaw.i18n.getLocalizedString('filesize_key_B');
		}
		else {
			val = '--';
		}

		return val.replace('.', iaw.i18n.getLocalizedString('filesize_key_decimal'));
	},

	/**
	 * Show license dialog.
	 *
	 * @param element	element {type: 'application/vnd.adobe.element.template+dcx'}
	 * @param contentId template Id
	 * @skipQuotaCheck 	skip quota check
	 * @licenseType 	license type
	 */
	showLicenseDialog: function(element, contentId, skipQuotaCheck, libraryId, renditionDetails, licenseType) {
		var PURCHASE_DIALOG_INIT_EVENT_NAME = 'com.adobe.stock.panel.license.init';
		var PURCHASE_DIALOG_PERFORM_EVENT_NAME = 'com.adobe.stock.panel.license.perform';
		var PURCHASE_DIALOG_CLOSED_EVENT_NAME = 'com.adobe.stock.panel.license.closed';
		var PURCHASE_DIALOG_EXTENSION_ID = 'com.adobe.stock.panel.licensing';

		return new Promise(function(resolve, reject) {
			var csInterface = iaw.cepUtil.csInterface;
			if (!csInterface) {
				reject();
				return;
			}
			// Register for event to know when Purchase dialog loads & pass on the required information
			function onPurchaseDialogInit(event) {
				csInterface.removeEventListener(PURCHASE_DIALOG_INIT_EVENT_NAME, onPurchaseDialogInit);
				var csEvent = new CSEvent(PURCHASE_DIALOG_PERFORM_EVENT_NAME, 'APPLICATION', 'CCInAppCmdN', csInterface.getExtensionID());
				// TODO: get rid of stockSearchAsset once Stock search is working fine
				csEvent.data = {'elementType': element.type, 'contentId': String(contentId), 'skipQuotaCheck': skipQuotaCheck, 'licenseType': licenseType, 'addToLibraryID': libraryId, 'renditionDetails': renditionDetails};
				csInterface.dispatchEvent(csEvent);
				iaw.log.logJSON('Dispatch CEP event to start purchase dialog: ', csEvent);
			}
			csInterface.addEventListener(PURCHASE_DIALOG_INIT_EVENT_NAME, onPurchaseDialogInit);

			// Register for event to know when Purchase dialog closes & get purchase information
			function onPurchaseDialogClosed(event) {
				iaw.log.logJSON('Data from purchase dialog: ', event);
				csInterface.removeEventListener(PURCHASE_DIALOG_CLOSED_EVENT_NAME, onPurchaseDialogClosed);
				csInterface.removeEventListener('com.adobe.csxs.events.ExtensionUnloaded', onPurchaseDialogClosed);

				// If this callback is due to ExtensionUnloaded event
				if (event.type === 'com.adobe.csxs.events.ExtensionUnloaded') {
					var xmlParser = new DOMParser();
					var xmlDoc = xmlParser.parseFromString(event.data, 'text/xml');
					// Check for the extension that go unloaded
					if (xmlDoc.getElementsByTagName('Id')[0].childNodes[0].nodeValue !== PURCHASE_DIALOG_EXTENSION_ID) {
						return;
					}
				}

				var eventData = event.data;
				if (eventData && eventData.didFinish) {
					resolve(eventData.data);
				}
				else {
					reject(eventData && eventData.data);
				}
			}
			csInterface.addEventListener(PURCHASE_DIALOG_CLOSED_EVENT_NAME, onPurchaseDialogClosed);
			csInterface.addEventListener('com.adobe.csxs.events.ExtensionUnloaded', onPurchaseDialogClosed);

			csInterface.requestOpenExtension(PURCHASE_DIALOG_EXTENSION_ID);
		});
	},

	/**
	 * Creates a string like "ccx.open recent grid" or "ccx.start dragdrop" to pass to Photoshop
	 * for Highbeam logging of open actions.
	 *
	 * @param context			The general context such as recent, cc files, open dialog, etc
	 * @param contextDetails	More details about the context, e.g. grid or list view. Optional.
	 * @return A string combining the telling part of the extension id and the contet and details.
	 */
	getPipMethodString : function(context, viewMode) {
		var method = iaw.cepUtil.csInterface.getExtensionID().replace('com.adobe.', '') + ' ' + context;
		if (viewMode) {
			method += ' ' + viewMode;
		}
		return method;
	},

	/**
	 * Object.assing polyfill from https://developer.mozilla.org/en-US/docs/Web/JavaScript/Reference/Global_Objects/Object/assign
	 */
	assign: function(target) {
		'use strict';
		if (target === undefined || target === null) {
			throw new TypeError('Cannot convert undefined or null to object');
		}

		var output = Object(target);
		for (var index = 1; index < arguments.length; index++) {
			var source = arguments[index];
			if (source !== undefined && source !== null) {
				for (var nextKey in source) {
					if (source.hasOwnProperty(nextKey) && source[nextKey] !== undefined) {
						output[nextKey] = source[nextKey];
					}
				}
			}
		}
		return output;
	},

	/**
	 * Merges two (or more) objects, giving the last one precedence
	 * @param {Object} target
	 * @param {Object} source
	 */
	merge: function(target, source) {
		if (typeof target !== 'object') {
			target = {};
		}

		var sourceProperty;

		for (var property in source) {
			if (source.hasOwnProperty(property)) {
				sourceProperty = source[property];

				if (typeof sourceProperty === 'object') {
					target[property] = iaw.util.merge(target[property], sourceProperty);
				}

				target[property] = sourceProperty;
			}
		}

		for (var a = 2, l = arguments.length; a < l; a++) {
			iaw.util.merge(target, arguments[a]);
		}

		return target;
	},


	/**
	 * Get template data per template id
	 * @param templateId		template id
	 * @return A object of template data
	 */
	getTemplateData: function(templateId) {
		var templateData;
		var templates = iaw.store.get('templates');
		if (templates && templates.length > 0) {
			templates.forEach(function(template) {
				if (template.id === templateId) {
					templateData = template;
				}
			});
		}
		return templateData;
	},

	/**
	 * Set template data per uuid
	 * @param templateId		template id
	 * @return A object of template data
	 */
	setTemplateData: function(uuid, templateData) {
		var templateIndex = iaw.store.get(['templateLUT', uuid]);
		iaw.store.set(['templates', templateIndex], templateData);
	},

	/**
	 * Animate a scrollTo action of the provided element given the provided offset
	 * TODO this should move to a more common location, perhaps with spectre if this disclosure widget makes it there
	 *
	 * @param {DOM.element} element scroll container element
	 * @param {number} offset positive or negative distance to scroll from current
	 * @param {number=} duration
	 * @param {Function=} callback
	 */
	smoothScroll: function(element, offset, duration, callback) {

		function easeInOutQuad(t, b, c, d) {
			t /= d/2;
			if (t < 1) {
				return c/2*t*t + b;
			}
			t--;
			return -c/2 * (t*(t-2) - 1) + b;
		}

		var start = element.scrollTop,
			currentTime = 0,
			increment = 20,
			val;

		duration = (typeof (duration) === 'undefined') ? 500 : duration;

		function animateScroll() {
			currentTime += increment;
			val = easeInOutQuad(currentTime, start, offset, duration);
			element.scrollTop = val;

			if (currentTime < duration) {
				requestAnimationFrame(animateScroll);
			}
			else if (typeof callback === 'function') {
				callback();
			}
		}

		animateScroll();
	},

	/**
	 * Launch CCX process if not running.
	 * callback is a funcion with parameter status code:
	 *   0: means the process has been running
	 *   1: indicates the process is just launched and ready
	 *   2: timeout or failed to launch process, like Thor is upgrading, or it's slow to get CCX Process startup (longer than 2 seconds)
	 *   3: Thor or ccx process is not installed at all
	 */
	launchCCXProcess: function(callback) {
		if (iaw.util.ccxProcessLaunchPending || !window.__adobe_cep__) {
			return;
		}

		var initializedTimeout;
		var initializedMessage = 'vulcan.SuiteMessage.ccxprocess.Initialized';

		callback = callback || function() {};
		iaw.util.ccxProcessLaunchPending = true;

		function onFinished(status) {
			callback(status);
			iaw.util.ccxProcessLaunchPending = false;
		}

		function started() {
			clearTimeout(initializedTimeout);
			iaw.util.ccxProcessLaunchPending = false;
			VulcanInterface.removeMessageListener(initializedMessage, started);
			onFinished(1);
		}

		function startLaunchCCXProcess() {
			VulcanInterface.addMessageListener(initializedMessage, started);
			initializedTimeout = setTimeout(function() {
				VulcanInterface.removeMessageListener(initializedMessage, started);
				onFinished(2);
			},
			1000);
			VulcanInterface.launchApp('ccxprocess', false);
		}

		if (VulcanInterface.isAppInstalled('ccxprocess')) {
			iaw.util.isCCXProcessRunning(function(isRunning) {
				if (!isRunning) {
					iaw.log.console('Start to launch CCX Process');
					startLaunchCCXProcess();
				}
				else {
					onFinished(0);
				}
			});
		}
		else {
			onFinished(3);
		}
	},

	/**
	 * Check if CCX process is installed.
	 */
	isCCXProcessInstalled: function(callback) {
		return VulcanInterface.isAppInstalled('ccxprocess');
	},

	/**
	 * Check if CCX process is running.
	 */
	isCCXProcessRunning: function(callback) {
		if (!iaw.util.isWindowsOS() && VulcanInterface.isAppRunning('ccxprocess')) {
			// Ignore VulcanInterface.isAppRunning on Windows due to https://jira.corp.adobe.com/browse/CEP-510
			return callback(true);
		}

		// VulcanInterface.isAppRunning is not reliable to know the running status. Sometimes it returns false for the running CCX Process on Mac.
		// Workaround is to check if CCX Process is able to respond to a ping/pong message in 200ms.
		var pingMessage = 'vulcan.SuiteMessage.ccxprocess.in.request.app.ping';
		var pongMessage = 'vulcan.SuiteMessage.ccxprocess.out.response.app.ping';
		var pingMessageTimeout;

		function pongHanlder() {
			clearTimeout(pingMessageTimeout);
			VulcanInterface.removeMessageListener(pongMessage, pongHanlder);
			callback(true);
		}

		VulcanInterface.addMessageListener(pongMessage, pongHanlder);
		pingMessageTimeout = setTimeout(function() {
			VulcanInterface.removeMessageListener(pongMessage, pongHanlder);
			callback(false);
		}, 200);

		var message = new VulcanMessage(pingMessage);
		message.setPayload('');
		VulcanInterface.dispatchMessage(message);
	}
};

/*************************************************************************
 * ADOBE CONFIDENTIAL
 * ___________________
 *
 *  Copyright 2016 Adobe Systems Incorporated
 *  All Rights Reserved.
 *
 * NOTICE:  All information contained herein is, and remains
 * the property of Adobe Systems Incorporated and its suppliers,
 * if any.  The intellectual and technical concepts contained
 * herein are proprietary to Adobe Systems Incorporated and its
 * suppliers and are protected by all applicable intellectual property
 * laws, including trade secret and copyright laws.
 * Dissemination of this information or reproduction of this material
 * is strictly forbidden unless prior written permission is obtained
 * from Adobe Systems Incorporated.
 **************************************************************************/
(function() {
	var fnft = {

		data: { },
		earlyKeyEventsProcessed: false,

		RWD_SMALL: 0,
		RWD_LARGE: 1,
		NUMBER_OF_ITEMS_ABOVE_FOLD: 12,
		LOG_PREFIX:						'CCX-FNFT :: ',
		CCXP_VULCAN_SPECIFIER:			'CCXP',
		VULCAN_STOCK_TEMPLATE_REQUEST:	'ccxprocess.StockTemplateRequest',
		VULCAN_STOCK_TEMPLATE_RESPONSE:	'ccxprocess.StockTemplateResponse',
		STOCK_RESPONSE_TIMEOUT:			1000, // 1 seconds
		StockTimeoutHandler:			null,

		/**
		 * Event handler for updating the calculation data from the host for AI/ID field calculations.
		 *
		 * @param evt 	event data object
		 */
		handleUpdateCalcData: function(evt) {
			var evtData = null;

			try {
				evtData = (typeof evt.data === 'string') ? JSON.parse(evt.data) : evt.data;
			}
			catch (err) {
				iaw.log.exception('Host calculation update handler exception: '+err.message);
			}
			if (evtData) {
				iaw.store.set(['host-calc-update'], evtData);
			}
		},

		/**
		 * Event handler for early key events that happened before the dialog initializes (iaw.cepUtil.events.EARLYKEYEVENTS)
		 * @param evt 	event data object
		 */
		handleEarlykeyEvents: function(evt) {
			iaw.cepUtil.csInterface.removeEventListener(iaw.cepUtil.events.EARLYKEYEVENTS, iaw.fnft.handleEarlykeyEvents);

			var evtData = null;

			try {
				evtData = (typeof evt.data === 'string') ? JSON.parse(evt.data) : evt.data;
			}
			catch (err) {
				iaw.log.exception('Host early key events handler exception: ' + err.message);
			}

			if (evtData && evtData.keyCodes.indexOf(iaw.a11y.Keys.ENTER) > -1) {
				iaw.fnft.createNewDocumentFromSelectedTemplate(false);
				iaw.fnft.closeExtOnStatus('true');
				return;
			}

			if (evtData && evtData.keyCodes.indexOf(iaw.a11y.Keys.ESC) > -1) {
				iaw.fnft.closeExtOnStatus('true');
				return;
			}

			// Tell the panel that we are done processing early key events
			iaw.fnft.earlyKeyEventsProcessed = true;
		},

		/**
		 * Generate a default data set if the Stock pull fails.
		 *
		 * @return an object representing the default data set.
		 */
		generateDefaultStockData: function(hostData) {
			var defaultData = {
				'nb_results': 0,
				'templates': [],
				'expirationDTS': '2015-11-01T00:00:00.000+00:00',
				'version': hostData.radarVersion || '0.0.0'
			};

			return defaultData;
		},

		/**
		 * Create the filter tab elements for the file new from template nav bar
		 *
		 * @param hostID			application host ID string
		 * @return An ordered array of the nav bar filter elements
		 */
		generateFilters: function(hostID) {
			var filters;
			switch (hostID) {
				case 'ILST':
					filters = [
						{type: 'recent',	name: 'newdoc_filter_recent'},
						{type: 'saved',     name: 'newdoc_filter_saved'},
						{type: 'mobile',	name: 'newdoc_filter_mobile'},
						{type: 'web',		name: 'newdoc_filter_web'},
						{type: 'print',		name: 'newdoc_filter_print'},
						{type: 'film',		name: 'newdoc_filter_film'},
						{type: 'art',		name: 'newdoc_filter_art'}
					];
					break;
				case 'PHXS':
					filters = [
						{type: 'recent',	name: 'newdoc_filter_recent'},
						{type: 'saved',     name: 'newdoc_filter_saved'},
						{type: 'photo',		name: 'newdoc_filter_photo'},
						{type: 'print',		name: 'newdoc_filter_print'},
						{type: 'art',		name: 'newdoc_filter_art'},
						{type: 'web',		name: 'newdoc_filter_web'},
						{type: 'mobile',	name: 'newdoc_filter_mobile'},
						{type: 'film',		name: 'newdoc_filter_film'}
					];
					break;
				case 'IDSN':
					filters = [
						{type: 'recent',	name: 'newdoc_filter_recent'},
						{type: 'saved',     name: 'newdoc_filter_saved'},
						{type: 'print',		name: 'newdoc_filter_print'},
						{type: 'web',		name: 'newdoc_filter_web'},
						{type: 'mobile',	name: 'newdoc_filter_mobile'}
					];
					break;
				default:
					filters = [
						{type: 'recent',	name: 'newdoc_filter_recent'},
						{type: 'saved',     name: 'newdoc_filter_saved'},
						{type: 'mobile',	name: 'newdoc_filter_mobile'},
						{type: 'web',		name: 'newdoc_filter_web'},
						{type: 'print',		name: 'newdoc_filter_print'},
						{type: 'photo',		name: 'newdoc_filter_photo'},
						{type: 'film',		name: 'newdoc_filter_film'},
						{type: 'art',		name: 'newdoc_filter_art'}
					];
					break;
			}
			return filters;
		},

		/**
		 * Get the icon font element associated with the corresponding ID.
		 *
		 * @param elementID	unique identifier string of the UI element
		 */
		getThumbnailIcon: function(elementID) {
			var iconMap = {
				'recent': 'SP_PresetNewFromPreset.png',
				'mobile': 'SP_PresetMobileAppDoc.png',
				'web': 'SP_PresetWebDoc.png',
				'print': 'SP_PrintDoc.png',
				'photo': 'SP_PresetPhotoDoc.png',
				'film': 'SP_PresetFilmVideo.png',
				'art': 'SP_PresetCustom.png'
			};
			var icon = iconMap[elementID] || 'CCX_Start_DefaultThumb_other.png';

			return 'images/thumbs/'+icon;
		},

		/**
		 * Get the JSON data block that matches the selected template.
		 *
		 * @param templateUUID	unique identifier string of the template
		 */
		getTemplateData: function(templateUUID) {

			var templateData  = null;

			if (templateUUID) {
				var dataIndex = iaw.store.get(['presetLUT', templateUUID]);

				if (dataIndex !== undefined ) {
					templateData = iaw.store.get('presets', dataIndex);
				}
				else {
					dataIndex = iaw.store.get(['templateLUT', templateUUID]);

					if (dataIndex !== undefined ) {
						templateData = iaw.store.get('templates', dataIndex);
					}
				}
			}
			return templateData;
		},

		/**
		 * Handle status coming back from the host ExtendScript method,
		 * closes the extension is status is 'true'. Remember callbacks/continuation
		 * methods from ExtendScript always send params as strings.
		 * For FNFT we have to close 2 extensions - the Start & FNFT.
		 *
		 * @param status       string containing 'false' if cancelled/error, 'true' otherwise
		 */
		closeExtOnStatus: function(status) {
			if ((status.toLowerCase() === 'true') && window.__adobe_cep__) {
				//iaw.analytics.ingest.logScreenStateEvent('close-auto');
				window.__adobe_cep__.closeExtension();
			}
		},

		/*
		 * Create a new Preset from the currently selected document settings
		 *
		 * @param name the name of the preset to create
		 * @return an object representing the created template set.
		 */
		createPreset: function(name, callback) {
			// get the selected template
			var templateData = iaw.fnft.getTemplateData(iaw.store.get(['input', 'selected-item']));
			if (!templateData) {
				try {
					templateData = JSON.parse(iaw.store.get('last-saved-template-data') || iaw.store.get('last-deleted-template-data'));
				}
				catch (e) {
					templateData = {isPreset: true};
				}
			}
			iaw.store.set('last-saved-template-data', JSON.stringify(templateData));

			var hostID  = iaw.store.get(['host', 'hostID']);
			var settings = iaw.store.get(['input', 'settings']);

			var tempObj = {};

			// merge templateData to tempObj
			iaw.util.assign(tempObj, templateData);

			if (templateData.isPreset && settings) {
				// merge custom settings to tempObj
				iaw.util.assign(tempObj, settings);
			}

			tempObj['template_category'] = 'saved';

			tempObj.title = name;
			tempObj.name = name;
			tempObj.group = 'user';

			if (hostID === 'PHXS') {
				if (templateData.isPreset) {
					if (window.__adobe_cep__) {
						iaw.cepUtil.evalExtendScriptWithParams('CCXWelcomeXSHost_PHXS.createPreset', tempObj, name, callback);
					}
					else {
						callback(tempObj);
					}
				}
			}
			else if (hostID === 'IDSN') {
				if (templateData.isPreset) {
					if (window.__adobe_cep__) {
						iaw.cepUtil.sendEvent( iaw.cepUtil.events.SAVEPRESET, tempObj);
						callback(tempObj);
					}
				}
			}

			return tempObj;
		},

		deletePreset: function(name) {
			if (!window.__adobe_cep__) return;
			var hostID  = iaw.store.get(['host', 'hostID']);
			var templateData = iaw.fnft.getTemplateData(iaw.store.get(['input', 'selected-item']));
			iaw.store.set('last-deleted-template-data', JSON.stringify(templateData));
			switch (hostID) {
				case 'PHXS':
					iaw.cepUtil.evalExtendScriptWithParams('CCXWelcomeXSHost_PHXS.deletePreset', name, iaw.fnft.closeExtOnStatus);
					break;
				case 'IDSN':
					iaw.cepUtil.sendEvent( iaw.cepUtil.events.DELETEPRESET, name);
					break;
			}
		},

		/*
		 * Create a new document from the currently selected template
		 *
		 * @param showDialog		boolean parameter indicating if the native document
		 *							dialog should be shown or not
		 */
		createNewDocumentFromSelectedTemplate: function(showDialog) {
			var self = this;
			// validate required parameter
			showDialog = showDialog || false;

			// get the selected template
			var templateData = iaw.fnft.getTemplateData(iaw.store.get(['input', 'selected-item']));

			if (templateData) {
				var hostID  = iaw.store.get(['host', 'hostID']);
				var docName = iaw.store.get(['input', 'doc-name']);
				var settings = iaw.store.get(['input', 'settings']);
				var tempObj = {};

				// set the document name for the new document
				tempObj.documentName = docName;
				tempObj.showDialog = showDialog;

				// merge templateData to tempObj
				iaw.util.assign(tempObj, templateData);

				if (templateData.isPreset && settings) {
					// merge custom settings to tempObj
					iaw.util.assign(tempObj, settings);
				}
				else if (!templateData.url) {
					var status = iaw.libraryManager.getTemplateStatus(templateData.id);
					if (status) {
						tempObj.url = status.path;
						tempObj.elementRef = status.elementRef;
					}
				}

				switch (hostID) {
					case 'PHXS':

						var fnftSettings = iaw.store.get(['host', 'fnftSettings']);
						if (fnftSettings.psSupportsDocCreationViaCEPEvents) {
							// New approach
							tempObj.pipMethod = iaw.util.getPipMethodString('stocktemplate', null);

							iaw.log.console(self.LOG_PREFIX + 'Sending data to create new doc: ' + JSON.stringify(tempObj));

							var psEventType = templateData.isPreset ? iaw.cepUtil.events.NEWDOCFROMTEMPLATE : iaw.cepUtil.events.PREVIEWDOCFROMTEMPLATE;
							iaw.cepUtil.sendEvent(psEventType, JSON.stringify(tempObj));
						}
						else {
							// LEGACY APPROACH - Keep until know will only ship w/ PS that has support for the above (probably PS 19.1)
							if (templateData.isPreset) {
								iaw.cepUtil.evalExtendScriptWithParams('CCXWelcomeXSHost_PHXS.createDocumentFromTemplate',
									tempObj, docName, showDialog, iaw.fnft.closeExtOnStatus);
							}
							else {
								var pipMethod = iaw.util.getPipMethodString('stocktemplate', null);
								window.__adobe_cep__.evalScript('CCXWelcomeXSHost_PHXS.openDocumentWithPath("'+tempObj.url+'","'+pipMethod+'",true,"'+templateData.elementRef+'")', iaw.fnft.closeExtOnStatus);
							}
						}
						break;

					default:
						iaw.log.console(self.LOG_PREFIX + 'Sending data to create new doc: ' + JSON.stringify(tempObj));
						var eventType = iaw.cepUtil.events.NEWDOCFROMTEMPLATE;

						if (settings.actionTrigger == 'preview') {
							eventType = iaw.cepUtil.events.PREVIEWDOCFROMTEMPLATE;
						}

						iaw.cepUtil.sendEvent(eventType, JSON.stringify(tempObj));
						break;
				}
			}
		},

		/**
		 * Get template data from the Adobe Stock service.
		 * @param hostData	host data passed from the application
		 * @param cb		callback method
		 */
		getStockData: function(hostData, cb) {
			if (!hostData.adobeGUID || hostData.adobeGUID.length === 0) {
				iaw.log.console(this.LOG_PREFIX + 'No user. Likely FRL or signed out.');
				// Offline or FRL case.
				cb(null, {});
			}
			var self = this;
			// var startTimestamp = Date.now();
			var onFinish = function(err, data) { //eslint-disable-line func-style
				if (err) {
					iaw.log.console(self.LOG_PREFIX + err);
				}
				// var durationInMilliSec = Date.now() - startTimestamp;

				// iaw.log.debug(self.LOG_PREFIX + 'It costs ' + durationInMilliSec + ' ms to get stock response from CCXProcess.');
				cb(data);
			};

			var uuid = iaw.util.generateGUID();
			var responder = {};
			responder.handler = function(msg) { //eslint-disable-line func-style
				var responseData = VulcanInterface.getPayload(msg);
				var stockJSON;
				var parsedData;

				// parse the JSON response
				try {
					parsedData = JSON.parse(responseData);
					// iaw.log.debug(self.LOG_PREFIX + 'Receiving ' + self.VULCAN_STOCK_TEMPLATE_RESPONSE + ' [' + parsedData.requestId + '], path is ' + parsedData.path);
				}
				catch (error) {
					// iaw.log.debug(self.LOG_PREFIX + 'Invalid JSON string:' + responseData);
					return;
				}
				// validate
				if (parsedData && parsedData.requestId === uuid) {
					// Clear timeout only if the response has the expected requestid.
					clearTimeout(self.StockTimeoutHandler);
					var err = null;

					if (parsedData.path) {
						// read in the locally written JSON data from Adobe Stock
						// and adjust image paths to the local file system
						stockJSON = iaw.json.readLocalJSONFile(parsedData.path);

						if (stockJSON && stockJSON.templates) {
							for (var i = 0; i < stockJSON.templates.length; i++) {
								var template = stockJSON.templates[i];
								var parentFolder = parsedData.path.substring(0, parsedData.path.lastIndexOf('/'));
								if (template['thumbnail_url'] ) {
									template['thumbnail_url'] = parentFolder && parentFolder + '/' + template['thumbnail_url'];
								}
								if (template.previews) {
									for (var j = 0; j < template.previews.length; j++) {
										var preview = template.previews[j];
										if (preview && preview.url) {
											preview.url = parentFolder && parentFolder + '/' + preview.url;
										}
									}
								}
							}
						}
					}
					else {
						err = 'path is missing, parsedData=' + JSON.stringify(parsedData);
					}
					// clear IPC listeners
					VulcanInterface.removeMessageListener(VulcanMessage.TYPE_PREFIX + self.VULCAN_STOCK_TEMPLATE_RESPONSE, responder.handler);
					// finish out
					onFinish(err, stockJSON);
				}
			};

			// Add message lisener for Stock response
			VulcanInterface.addMessageListener(VulcanMessage.TYPE_PREFIX + self.VULCAN_STOCK_TEMPLATE_RESPONSE, responder.handler);

			// Send Stock request
			var stockParams = {
				productCode:		hostData.hostID,
				productVersion:		hostData.appVersion,
				productLanguage:	hostData.language,
				countryCode:		hostData.countryCode,
				subscriptionStatus:	hostData.accountStatus,
				requestId:			uuid,
				featureFlag: 		false,
				release: 			'CCXStart-1-5'
			};

			if (stockParams.subscriptionStatus === 'trial') {
				stockParams.trialEndDTS = iaw.util.getTrialEndDate(hostData.secondsLeftInTrial);
			}

			var params = {
				stockParams:			stockParams,
				requestId:				uuid,
				userTrackingEnabled:	hostData.userTrackingEnabled
			};
			var StockRequestMsg = new VulcanMessage(VulcanMessage.TYPE_PREFIX + self.VULCAN_STOCK_TEMPLATE_REQUEST);

			StockRequestMsg.setPayload(JSON.stringify(params));
			VulcanInterface.dispatchMessage(StockRequestMsg);
			iaw.log.console(self.LOG_PREFIX + 'Sending ' + self.VULCAN_STOCK_TEMPLATE_REQUEST + ' [' + uuid + ']');
			var relaunched = false;
			// Set timeout
			function timeoutHandler() {
				// Check and launch CCXProcess
				if (!relaunched) {
					relaunched = true;
					iaw.util.launchCCXProcess();
					VulcanInterface.dispatchMessage(StockRequestMsg);
					self.StockTimeoutHandler = setTimeout(timeoutHandler, self.STOCK_RESPONSE_TIMEOUT);
					return;
				}

				VulcanInterface.removeMessageListener(VulcanMessage.TYPE_PREFIX + self.VULCAN_STOCK_TEMPLATE_RESPONSE, responder.handler);
				onFinish('It is timeout to get stock response from CCX Process.');
			}
			self.StockTimeoutHandler = setTimeout(timeoutHandler, self.STOCK_RESPONSE_TIMEOUT);
		}
	};

	window.iaw = window.iaw || {};
	window.iaw.fnft = fnft;
}());

/*************************************************************************
 * ADOBE CONFIDENTIAL
 * ___________________
 *
 *  Copyright 2016 Adobe Systems Incorporated
 *  All Rights Reserved.
 *
 * NOTICE:  All information contained herein is, and remains
 * the property of Adobe Systems Incorporated and its suppliers,
 * if any.  The intellectual and technical concepts contained
 * herein are proprietary to Adobe Systems Incorporated and its
 * suppliers and are protected by all applicable intellectual property
 * laws, including trade secret and copyright laws.
 * Dissemination of this information or reproduction of this material
 * is strictly forbidden unless prior written permission is obtained
 * from Adobe Systems Incorporated.
 **************************************************************************/

iaw.fnftEvents = {
	CUSTOMIZE_DOC:		'host-more-options',
	CREATE_DOC:			'host-create-template-doc',
	CANCEL_DOC:			'host-close-fnft',
	SCREEN_TEMPLATE:	'host-screen-template',
	FILTER:				'filter-templates',
	PREVIEW_DOC:        'host-preview-template-doc'
};

/**
 * Handle the "Customize" button event
 */
riotctrl.on(iaw.fnftEvents.CUSTOMIZE_DOC, function() {
	if (window.__adobe_cep__) {
		iaw.fnft.createNewDocumentFromSelectedTemplate(true);
	}
});

/**
 * Handle the "Create Document" button event.
 */
riotctrl.on(iaw.fnftEvents.CREATE_DOC, (function() {
	var isCoolingDown = false;
	var coolDownTimer = 7000;

	return function() {
		if (window.__adobe_cep__) {
			
			if (isCoolingDown) return;

			isCoolingDown = true;
			setTimeout( function() {
				isCoolingDown = false;
			}, coolDownTimer);

			var template = iaw.fnft.getTemplateData(iaw.store.get(['input', 'selected-item']));
			var hostID = iaw.store.get(['host', 'hostID']);

			if (hostID == 'IDSN') {
				var settings = iaw.store.get(['input', 'settings']);
				settings.actionTrigger = 'actionButton';
				iaw.store.set(['input', 'settings'], settings);
			}
			
			if (template && !template.isPreset) {
				if (hostID !== 'PHXS') {
					iaw.localstorage.setUserItem('templateLUT_' + template.id, new Date().getTime());
				}
				iaw.analytics.ingest.logFNFTActionClickedEvent('open-template', template);
				iaw.analytics.pip.logFNFTDataGroupEvent('Open Template', {
					id: String(template.id),
					name: template.title,
					templateCategory: template.template_category.toString(),
					activeTab: template.activeFilter
				});
			}
			iaw.fnft.createNewDocumentFromSelectedTemplate();
		}
		
	};
})());

/**
 * Handle the "Close" button event.
 */
riotctrl.on(iaw.fnftEvents.CANCEL_DOC, function() {
	if (!window.__adobe_cep__) return;
	
	window.__adobe_cep__.closeExtension();
});

/**
 * Handle the download template button event.
 */
riotctrl.on(iaw.fnftEvents.SCREEN_TEMPLATE, function() {
	var template = iaw.store.get(['input', 'preview-item']);
	iaw.analytics.ingest.logFNFTActionClickedEvent('download', template);
	var showScreen = iaw.localstorage.getUserItem('fnft.showFilesizeWarning');

	showScreen = typeof showScreen === 'boolean' ? showScreen : true;
	if (!showScreen) {
		iaw.analytics.ingest.logFNFTActionClickedEvent('license-template', template);
		riotctrl.trigger('host-license-template');
		return;
	}

	if (template.size / 1048576 > 100) {
		// warn the user that this is pretty big
		iaw.analytics.ingest.logFNFTActionClickedEvent('too-large-render', template);
		riotctrl.trigger('host-download-template--large');
		return;
	}
	// start the licensing process
	iaw.analytics.ingest.logFNFTActionClickedEvent('license-template', template);
	riotctrl.trigger('host-license-template');
});

/**
* Handle the preview document event.
**/

riotctrl.on(iaw.fnftEvents.PREVIEW_DOC, function() {
	if (window.__adobe_cep__) {
		iaw.fnft.createNewDocumentFromSelectedTemplate(true);
	}
});

//# sourceMappingURL=fnft-iaw.js.map
