package org.thunlp.crawler;

public class StubCrawlerListener implements CrawlerListener {
	public int availableWorkers = -1;
	public byte[] crawledContent = null;
	public String crawledUrl = null;
	public String crawledIp = null;
	public String[] headers = null;
	public Object customData = null;
	public int nFailed = 0;
	public int nSuccess = 0;

	public int workersCapacity = 0;
	public int nWorkerQueue = 0;

	public StubCrawlerListener() {

	}

	public void handleFailed(String url, String ip, int httpStatusCode, Object customData) {
		nFailed++;
	}

	public void handleSuccess(String url, String ip, byte[] content, String[] responseHeaders, Object customData) {
		nSuccess++;
		crawledContent = content;
		crawledUrl = url;
		crawledIp = ip;
		this.headers = responseHeaders;
		this.customData = customData;
	}

	public void workerQueueAvailable(int hashcode, int capacity) {
		nWorkerQueue++;
	}

	public void workersAvailable(int capacity) {
		workersCapacity = capacity;
	}

}
