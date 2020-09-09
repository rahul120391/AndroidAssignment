package com.elyments.assessment;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

/**
 * Helper class for providing sample content for user interfaces
 * <p>
 */
public class EmployeeList {

		/**
		 * An array of sample (dummy) items.
		 */
		public static final List<Employee> ITEMS = new ArrayList<Employee>();

		/**
		 * A map of sample (dummy) items, by ID.
		 */
		public static final Map<Integer, Employee> ITEM_MAP = new HashMap<Integer, Employee>();

		/**
		 * The constant COUNT.
		 */
		private static final int COUNT = 25;

		static {
				// Add some sample items.
				for (int i = 1; i <= COUNT; i++) {
						addItem(createDummyItem(i));
				}
		}

		/**
		 * Add item.
		 *
		 * @param item the item
		 */
		private static void addItem(Employee item) {
				ITEMS.add(item);
				ITEM_MAP.put(item.id, item);
		}

		/**
		 * Create dummy item employee.
		 *
		 * @param position the position
		 * @return the employee
		 */
		private static Employee createDummyItem(int position) {
				return new Employee(position, "Employee " + position, makeDetails(position));
		}

		/**
		 * Make details string.
		 *
		 * @param position the position
		 * @return the string
		 */
		private static String makeDetails(int position) {
				StringBuilder builder = new StringBuilder();
				builder.append("Details about Employee: ").append(position);
				for (int i = 0; i < position; i++) {
						builder.append("\n More details information here.");
				}
				return builder.toString();
		}

}