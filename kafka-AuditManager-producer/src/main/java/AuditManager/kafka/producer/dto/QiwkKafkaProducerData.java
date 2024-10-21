package AuditManager.kafka.producer.dto;

import java.io.Serializable;

public class QiwkKafkaProducerData implements Serializable{

		/**
		 * 
		 */
		private static final long serialVersionUID = -2043481945746808638L;
		private ProducerMetadata metadata;
		private Object payload;
		
		
		
		
		public QiwkKafkaProducerData() {
			super();
			
		}




		public QiwkKafkaProducerData(ProducerMetadata metadata, Object payload) {
			super();
			this.metadata = metadata;
			this.payload = payload;
		}




		public ProducerMetadata getMetadata() {
			return metadata;
		}




		public void setMetadata(ProducerMetadata metadata) {
			this.metadata = metadata;
		}




		public Object getPayload() {
			return payload;
		}




		public void setPayload(Object payload) {
			this.payload = payload;
		}




		@Override
		public int hashCode() {
			final int prime = 31;
			int result = 1;
			result = prime * result
					+ ((metadata == null) ? 0 : metadata.hashCode());
			result = prime * result + ((payload == null) ? 0 : payload.hashCode());
			return result;
		}




		@Override
		public boolean equals(Object obj) {
			if (this == obj)
				return true;
			if (obj == null)
				return false;
			if (getClass() != obj.getClass())
				return false;
			QiwkKafkaProducerData other = (QiwkKafkaProducerData) obj;
			if (metadata == null) {
				if (other.metadata != null)
					return false;
			} else if (!metadata.equals(other.metadata))
				return false;
			if (payload == null) {
				if (other.payload != null)
					return false;
			} else if (!payload.equals(other.payload))
				return false;
			return true;
		}




		@Override
		public String toString() {
			return "QiwkKafkaProducerData [metadata=" + metadata + ", payload="
					+ payload + "]";
		}

}
