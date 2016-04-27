import { CancellablePromise } from 'metal-promise/src/promise/Promise';

/**
 * Image Editor History Entry
 */
class ImageEditorHistoryEntry {
	/**
	 * Constructor
	 */
	constructor(image) {
		this.dataPromise_ = new CancellablePromise((resolve, reject) => {
			if (image.url && !image.data) {
				this.loadData_(image.url)
					.then((imageData) => resolve(imageData));
			}
			else {
				resolve(image.data);
			}
		});
	}

	/**
	 * [loadData_ description]
	 * @param  {[type]} imageURL [description]
	 * @return {[type]}          [description]
	 */
	loadData_(imageURL) {
		return new CancellablePromise((resolve, reject) => {
			let bufferImage = new Image();

			bufferImage.onload = () => {
				let bufferCanvas = document.createElement('canvas');
	            let bufferContext = bufferCanvas.getContext('2d');

	            let height = bufferImage.height;
	            let width = bufferImage.width;

				bufferCanvas.width = width;
				bufferCanvas.height = height;

				bufferContext.drawImage(bufferImage, 0, 0, width, height);

				resolve(bufferContext.getImageData(0, 0, width, height));
			};

			bufferImage.src = imageURL;
		});
	}

	/**
	 * [getImageData description]
	 *
	 * @return {CancellablePromise} [description]
	 */
	getImageData() {
		return this.dataPromise_;
	}
}

export default ImageEditorHistoryEntry;